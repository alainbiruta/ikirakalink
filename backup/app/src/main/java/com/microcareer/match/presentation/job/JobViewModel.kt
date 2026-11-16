package com.microcareer.match.presentation.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microcareer.match.data.local.entity.JobEntity
import com.microcareer.match.data.repository.AppResult
import com.microcareer.match.data.repository.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val jobRepository: JobRepository
) : ViewModel() {

    private val _jobDetailsUiState = MutableStateFlow(JobDetailsUiState())
    val jobDetailsUiState: StateFlow<JobDetailsUiState> = _jobDetailsUiState.asStateFlow()

    private val _uiEvents = MutableSharedFlow<JobUiEvent>()
    val uiEvents: SharedFlow<JobUiEvent> = _uiEvents.asSharedFlow()

    // ══════════════════════════════════════════════════════
    // BUG 1 FIX — selectJob
    // Was: treating Flow<AppResult<JobEntity>> as a suspend
    //      that returns a nullable value — wrong on both counts.
    // Fix: collect the flow, map AppResult states correctly,
    //      populate ALL JobDetailsUiState fields from JobEntity.
    // ══════════════════════════════════════════════════════

    fun selectJob(jobId: String) {
        viewModelScope.launch {
            jobRepository.getJobById(jobId).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _jobDetailsUiState.value = JobDetailsUiState(isLoading = true)
                    }

                    is AppResult.Success -> {
                        _jobDetailsUiState.value = result.data.toDetailsUiState()
                    }

                    is AppResult.Error -> {
                        // If we already have content (from cache), keep it but clear loading.
                        // Only show error if we have nothing to show.
                        val current = _jobDetailsUiState.value
                        if (current.hasContent) {
                            // Cache hit already rendered — silently ignore network error
                            _jobDetailsUiState.value = current.copy(isLoading = false)
                        } else {
                            _jobDetailsUiState.value = JobDetailsUiState(
                                isLoading = false,
                                error     = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun clearSelectedJob() {
        _jobDetailsUiState.value = JobDetailsUiState()
    }

    fun retryLoadJobs() {
        val current = _jobDetailsUiState.value
        // Re-use the job title as a signal we had a job selected.
        // AppNavigation passes jobId so re-trigger via selectJob from nav side.
        // This is a no-op hook — nav layer calls selectJob(jobId) on retry.
    }

    // ══════════════════════════════════════════════════════
    // BUG 2 FIX — applyToJob
    // Was: calling applyForJob() as if it were a suspend —
    //      it returns Flow<AppResult<Boolean>>, not Unit.
    // Fix: collect the flow, handle Loading/Success/Error states,
    //      show applying spinner via isApplying flag.
    // ══════════════════════════════════════════════════════

    fun applyToJob(jobId: String) {
        viewModelScope.launch {
            jobRepository.applyForJob(jobId).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _jobDetailsUiState.update { it.copy(isApplying = true) }
                    }

                    is AppResult.Success -> {
                        _jobDetailsUiState.update { it.copy(isApplying = false) }
                        // Mark as applied in the UI immediately
                        _jobDetailsUiState.update { it.copy(isApplying = false) }
                        _uiEvents.emit(
                            if (result.isFromCache)
                                JobUiEvent.ShowSnackbar("You're offline — application will sync when connected")
                            else
                                JobUiEvent.ApplySuccess
                        )
                    }

                    is AppResult.Error -> {
                        _jobDetailsUiState.update { it.copy(isApplying = false) }
                        _uiEvents.emit(JobUiEvent.ShowSnackbar(result.message))
                    }
                }
            }
        }
    }

    // ══════════════════════════════════════════════════════
    // BUG 3 FIX — toggleSaveJobById
    // Was: always calling saveJob(), ignoring currentlySaved.
    //      User could never unsave a job from the detail screen.
    // Fix: branch on currentlySaved, update isSaved in UI state
    //      optimistically so the bookmark icon flips immediately.
    // ══════════════════════════════════════════════════════

    fun toggleSaveJobById(jobId: String, currentlySaved: Boolean) {
        viewModelScope.launch {
            // Optimistic UI update — flip the icon immediately
            _jobDetailsUiState.update { it.copy(isSaved = !currentlySaved) }

            try {
                if (currentlySaved) {
                    jobRepository.unsaveJob(jobId)
                } else {
                    jobRepository.saveJob(jobId)
                }
            } catch (e: Exception) {
                // Revert optimistic update on failure
                _jobDetailsUiState.update { it.copy(isSaved = currentlySaved) }
                _uiEvents.emit(JobUiEvent.ShowSnackbar("Failed to update saved status"))
            }
        }
    }
}

// ══════════════════════════════════════════════════════════
// JobEntity → JobDetailsUiState
// Keeps mapping logic out of the ViewModel body.
// ══════════════════════════════════════════════════════════

private fun JobEntity.toDetailsUiState(): JobDetailsUiState {
    val salaryFormatted = buildString {
        append("${currency} ${payment}")
        if (duration > 0) append(" / ${duration}d")
    }

    val postedDate = buildString {
        val diffMs   = System.currentTimeMillis() - createdAt
        val diffDays = TimeUnit.MILLISECONDS.toDays(diffMs)
        append(
            when {
                diffDays == 0L -> "Posted today"
                diffDays == 1L -> "Posted yesterday"
                diffDays < 7L  -> "Posted ${diffDays}d ago"
                diffDays < 30L -> "Posted ${diffDays / 7}w ago"
                else           -> "Posted ${diffDays / 30}mo ago"
            }
        )
    }

    return JobDetailsUiState(
        isLoading    = false,
        jobTitle     = title,
        company      = employerName ?: "",
        location     = location ?: "",
        salary       = salaryFormatted,
        jobType      = category,
        isRemote     = location?.contains("remote", ignoreCase = true) ?: false,
        postedDate   = postedDate,
        description  = description ?: "",
        requirements = requiredSkills,
        isSaved      = isSaved,
        isApplying   = false,
        error        = null
    )
}