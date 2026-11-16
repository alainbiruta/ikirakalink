package com.microcareer.match.presentation.employer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microcareer.match.data.local.entity.JobEntity
import com.microcareer.match.data.local.TokenManager
import com.microcareer.match.data.repository.AppResult
import com.microcareer.match.data.repository.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.microcareer.match.data.local.FcmNotificationStore

data class EmployerUiState(
    val isLoading   : Boolean                = false,
    val myJobs      : List<JobEntity>        = emptyList(),
    val applicants  : List<ApplicantUiModel> = emptyList(),
    val companyName : String?                = null,
    val error       : String?                = null
)

sealed class EmployerUiEvent {
    data class ShowSnackbar(val message: String) : EmployerUiEvent()
}

@HiltViewModel
class EmployerHomeViewModel @Inject constructor(
    private val jobRepository    : JobRepository,
    private val tokenManager     : TokenManager,
    private val notificationStore: FcmNotificationStore
) : ViewModel() {

    val fcmNotifications = notificationStore.notifications
        .stateIn(
            scope        = viewModelScope,
            started      = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun markNotificationsRead() = notificationStore.markAllRead()

    private val _uiState = MutableStateFlow(EmployerUiState())
    val uiState: StateFlow<EmployerUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<EmployerUiEvent>()
    val events: SharedFlow<EmployerUiEvent> = _events.asSharedFlow()

    init {
        _uiState.update { it.copy(companyName = tokenManager.getCompanyName()) }
        loadMyJobs()
        loadApplicants()
        observeJobsChanged()
    }

    // getEmployerJobs() → Flow<AppResult<...>> — must collect, all 3 branches
    fun loadMyJobs() {
        viewModelScope.launch {
            jobRepository.getEmployerJobs().collect { result ->
                when (result) {
                    is AppResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is AppResult.Success -> _uiState.update {
                        it.copy(isLoading = false, myJobs = result.data)
                    }
                    is AppResult.Error   -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _events.emit(EmployerUiEvent.ShowSnackbar(result.message))
                    }
                }
            }
        }
    }

    // getApplicants() → suspend AppResult<List<RemoteApplication>> — no collect needed.
    // RemoteApplication.toApplicantUiModel() handles all field mapping.
    fun loadApplicants() {
        viewModelScope.launch {
            when (val result = jobRepository.getApplicants()) {
                is AppResult.Loading -> Unit
                is AppResult.Success -> _uiState.update {
                    it.copy(applicants = result.data.map { it.toApplicantUiModel() })
                }
                is AppResult.Error   -> { /* secondary data — silently ignore */ }
            }
        }
    }

   

    fun toggleJobStatus(jobId: String) {
        viewModelScope.launch {
            try {
                val job = _uiState.value.myJobs.find { it.id == jobId } ?: return@launch
                val newStatus = if (job.status == "OPEN") "CLOSED" else "OPEN"
                jobRepository.updateJobStatus(jobId, newStatus)
                _uiState.update {
                    it.copy(myJobs = it.myJobs.map { j ->
                        if (j.id == jobId) j.copy(status = newStatus) else j
                    })
                }
            } catch (_: Exception) {
                _events.emit(EmployerUiEvent.ShowSnackbar("Failed to update job status"))
            }
        }
    }
    private fun observeJobsChanged() {
        viewModelScope.launch {
            jobRepository.jobsChanged.collect {
                refresh()
            }
        }
    }

    fun deleteJob(jobId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(myJobs = it.myJobs.filter { j -> j.id != jobId }) }
            try {
                jobRepository.deleteJob(jobId)
                _events.emit(EmployerUiEvent.ShowSnackbar("Job deleted"))
            } catch (_: Exception) {
                loadMyJobs()
                _events.emit(EmployerUiEvent.ShowSnackbar("Failed to delete job"))
            }
        }
    }
    fun acceptApplicant(applicantId: String, jobId: String) {
        viewModelScope.launch {
            try {
                jobRepository.updateApplicationStatus(applicantId, jobId, "ACCEPTED")
                updateApplicantStatus(applicantId, jobId, "ACCEPTED")
                _events.emit(EmployerUiEvent.ShowSnackbar("Applicant accepted!"))
            } catch (_: Exception) {
                _events.emit(EmployerUiEvent.ShowSnackbar("Failed to accept applicant"))
            }
        }
    }

    fun rejectApplicant(applicantId: String, jobId: String) {
        viewModelScope.launch {
            try {
                jobRepository.updateApplicationStatus(applicantId, jobId, "REJECTED")
                updateApplicantStatus(applicantId, jobId, "REJECTED")
                _events.emit(EmployerUiEvent.ShowSnackbar("Applicant declined"))
            } catch (_: Exception) {
                _events.emit(EmployerUiEvent.ShowSnackbar("Failed to update applicant"))
            }
        }
    }

    private fun updateApplicantStatus(applicantId: String, jobId: String, status: String) {
        _uiState.update {
            it.copy(applicants = it.applicants.map { a ->
                if (a.applicantId == applicantId && a.jobId == jobId) a.copy(status = status) else a
            })
        }
    }

    fun refresh() {
        loadMyJobs()
        loadApplicants()
    }
}