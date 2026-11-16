package com.microcareer.match.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.microcareer.match.data.local.entity.JobEntity
import com.microcareer.match.data.repository.AppResult
import com.microcareer.match.data.repository.JobRepository
import com.microcareer.match.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.microcareer.match.data.local.FcmNotification
import com.microcareer.match.data.local.FcmNotificationStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val isLoading        : Boolean         = false,
    val isRefreshing     : Boolean         = false,
    val jobs             : List<JobEntity> = emptyList(),
    val recommendations  : List<JobEntity> = emptyList(),
    val savedJobs        : List<JobEntity> = emptyList(),
    val appliedJobs      : List<JobEntity> = emptyList(),
    val isOffline        : Boolean         = false,
    val pendingSyncCount : Int             = 0,
    val error            : String?         = null,
    val currentSkip      : Int             = 0,
    val selectedCategory : String?         = null,
    val searchQuery      : String          = "",
)

sealed class HomeUiEvent {
    data class ShowSnackbar(val message: String)    : HomeUiEvent()
    data class NavigateToJobDetail(val jobId: String) : HomeUiEvent()
    object NavigateToLogin                          : HomeUiEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val jobRepository : JobRepository,
    private val networkMonitor: NetworkMonitor,
    private val notificationStore: FcmNotificationStore
) : ViewModel() {

    private val _uiState  = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEvents = MutableSharedFlow<HomeUiEvent>()
    val uiEvents: SharedFlow<HomeUiEvent> = _uiEvents.asSharedFlow()

    val fcmNotifications = notificationStore.notifications.stateIn(
        scope        = viewModelScope,
        started      = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun markNotificationsRead() = notificationStore.markAllRead()

    // FIX 1 & 2 & 5: Replace the plain Boolean isLoadingJobs flag with a
    // cancellable Job reference. Instead of guarding with a flag that has to be
    // manually reset in 3 places, we cancel the previous coroutine and start a
    // fresh one. This eliminates:
    //   • The deadlock where two collect() coroutines ran simultaneously
    //   • The fragile manual flag resets in setCategory/search/refresh
    //   • The early flag drop on cache-hit (Bug 6) — cancellation is immediate
    private var jobsCollectJob: Job? = null

    private var wasOffline = false

    init {
        observeConnectivity()
        observePendingSyncCount()
        observeSavedJobs()
        observeAppliedJobs()
        // FIX 4: observeJobsChanged now calls silentReload() instead of refresh()
        // so background job changes don't animate the pull-to-refresh spinner.
        observeJobsChanged()
        loadJobs()
        loadRecommendations()
        syncAppliedJobs()
        checkApplicationStatusNotifications()
    }

    // ── Observers ─────────────────────────────────────────

    private fun observeSavedJobs() {
        viewModelScope.launch {
            jobRepository.getSavedJobs().collect { jobs ->
                _uiState.update { it.copy(savedJobs = jobs) }
            }
        }
    }

    private fun observeAppliedJobs() {
        viewModelScope.launch {
            jobRepository.getAppliedJobs().collect { jobs ->
                _uiState.update { it.copy(appliedJobs = jobs) }
            }
        }
    }

    // FIX 4: Silent background reload — does NOT set isRefreshing=true so the
    // pull-to-refresh spinner doesn't animate on its own when a job is posted.
    private fun observeJobsChanged() {
        viewModelScope.launch {
            jobRepository.jobsChanged.collect {
                silentReload()
            }
        }
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            networkMonitor.isOnline.collect { isOnline ->
                _uiState.update { it.copy(isOffline = !isOnline) }
                if (isOnline && wasOffline) loadJobs(skip = 0)
                wasOffline = !isOnline
            }
        }
    }

    private fun observePendingSyncCount() {
        viewModelScope.launch {
            jobRepository.observePendingSyncCount().collect { count ->
                _uiState.update { it.copy(pendingSyncCount = count) }
            }
        }
    }

    // ── Load jobs ─────────────────────────────────────────

    fun loadJobs(
        skip     : Int     = 0,
        category : String? = _uiState.value.selectedCategory
    ) {
        // FIX 1, 2, 5, 6: Cancel any in-flight collection before starting a new one.
        // This replaces the Boolean flag approach entirely:
        //   • No deadlock — old coroutine is cancelled before new one starts
        //   • No manual flag resets needed in setCategory/search/refresh
        //   • No early guard drop on cache hit — cancellation is synchronous
        //   • No concurrent collect() calls on the same flow
        jobsCollectJob?.cancel()
        jobsCollectJob = viewModelScope.launch {
            jobRepository.getJobs(skip = skip, category = category).collect { result ->
                when (result) {
                    is AppResult.Loading -> _uiState.update {
                        it.copy(isLoading = true, error = null)
                    }
                    is AppResult.Success -> _uiState.update {
                        it.copy(
                            isLoading    = false,
                            isRefreshing = false,
                            jobs         = if (skip == 0) result.data else it.jobs + result.data,
                            currentSkip  = skip,
                            error        = null
                        )
                    }
                    is AppResult.Error -> _uiState.update {
                        it.copy(isLoading = false, isRefreshing = false, error = result.message)
                    }
                }
            }
        }
    }

    fun loadNextPage() {
        if (!_uiState.value.isLoading) {
            loadJobs(skip = _uiState.value.currentSkip + 20)
        }
    }

    // ── Recommendations ───────────────────────────────────

    fun loadRecommendations() {
        viewModelScope.launch {
            jobRepository.getRecommendations().collect { result ->
                if (result is AppResult.Success) {
                    _uiState.update { it.copy(recommendations = result.data) }
                }
            }
        }
    }

    // ── Category filter ───────────────────────────────────

    fun setCategory(category: String?) {
        // FIX 2 & 5: No manual flag reset needed — loadJobs() cancels the old
        // coroutine itself.
        _uiState.update { it.copy(selectedCategory = category) }
        loadJobs(skip = 0, category = category)
    }

    // ── Save / unsave ─────────────────────────────────────

    fun toggleSaveJob(jobId: String, currentlySaved: Boolean) {
        viewModelScope.launch {
            if (currentlySaved) jobRepository.unsaveJob(jobId)
            else jobRepository.saveJob(jobId)
            _uiState.update { state ->
                state.copy(jobs = state.jobs.map { job ->
                    if (job.id == jobId) job.copy(isSaved = !currentlySaved) else job
                })
            }
        }
    }

    // ── Apply ─────────────────────────────────────────────

    fun applyForJob(jobId: String) {
        viewModelScope.launch {
            jobRepository.applyForJob(jobId).collect { result ->
                when (result) {
                    is AppResult.Success -> {
                        _uiState.update { state ->
                            state.copy(jobs = state.jobs.map { job ->
                                if (job.id == jobId) job.copy(isApplied = true, isSaved = true)
                                else job
                            })
                        }
                        _uiEvents.emit(HomeUiEvent.ShowSnackbar("Applied successfully!"))
                    }
                    is AppResult.Error -> _uiEvents.emit(HomeUiEvent.ShowSnackbar(result.message))
                    else -> Unit
                }
            }
        }
    }

    // ── Refresh (user-triggered pull-to-refresh) ──────────

    fun refresh() {
        // Only refresh() sets isRefreshing=true — this is the explicit user gesture.
        // Background reloads (observeJobsChanged) use silentReload() instead.
        _uiState.update { it.copy(isRefreshing = true) }
        loadJobs(skip = 0)
        loadRecommendations()
    }

    // FIX 4: Silent background reload — no spinner, no isRefreshing flag.
    // Called when a job is posted/edited/deleted by an employer.
    private fun silentReload() {
        loadJobs(skip = 0)
        loadRecommendations()
    }

    // ── Search ────────────────────────────────────────────

    fun search(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.isBlank()) {
            // FIX 2 & 5: No manual flag reset — loadJobs() handles it.
            loadJobs(skip = 0)
        } else {
            // Cancel any in-flight jobs load before switching to search mode
            jobsCollectJob?.cancel()
            jobsCollectJob = viewModelScope.launch {
                jobRepository.searchJobs(query).collect { jobs ->
                    _uiState.update { it.copy(jobs = jobs, isLoading = false) }
                }
            }
        }
    }

    // FIX 7: Wrap syncAppliedJobs in try/catch so an exception doesn't silently
    // cancel the coroutine with no log output.
    private fun syncAppliedJobs() {
        viewModelScope.launch {
            try {
                jobRepository.syncAppliedJobsFromServer()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "syncAppliedJobs failed: ${e.message}")
            }
        }
    }

    private fun checkApplicationStatusNotifications() {
        viewModelScope.launch {
            try {
                val response = jobRepository.fetchMyApplicationsRaw() ?: return@launch
                for (app in response) {
                    val status = app["status"] as? String ?: continue
                    if (status != "ACCEPTED" && status != "REJECTED") continue

                    val appId    = app["id"] as? String ?: continue
                    val jobMap   = app["job"] as? Map<*, *>
                    val jobTitle = jobMap?.get("title") as? String ?: "a job"

                    // FIX: check by notification id — the exact field LazyColumn keys on.
                    // The old check used applicationId+type which could pass while the
                    // store already held a notification with this id, causing a duplicate
                    // key crash in the LazyColumn.
                    val notificationId = "${appId}_${status}"
                    val already = notificationStore.notifications.value
                        .any { it.id == notificationId }
                    if (already) continue

                    notificationStore.add(
                        FcmNotification(
                            id            = notificationId,
                            type          = if (status == "ACCEPTED") "application_accepted"
                            else "application_rejected",
                            title         = if (status == "ACCEPTED") "🎉 Application Accepted!"
                            else "Application Update",
                            body          = if (status == "ACCEPTED")
                                "Congratulations! You got the job: $jobTitle"
                            else
                                "Your application for $jobTitle was not selected.",
                            applicationId = appId,
                            receivedAt    = System.currentTimeMillis(),
                            isRead        = false
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "checkApplicationStatusNotifications: ${e.message}")
            }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
}