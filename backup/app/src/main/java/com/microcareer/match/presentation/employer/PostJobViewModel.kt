package com.microcareer.match.presentation.employer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microcareer.match.data.repository.AppResult
import com.microcareer.match.data.repository.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostJobUiState(
    val isLoading    : Boolean       = false,
    val isSuccess    : Boolean       = false,
    val error        : String?       = null,
    // ── Pre-filled when editing ──────────────────────────
    val title        : String        = "",
    val description  : String        = "",
    val location     : String        = "",
    val payment      : String        = "",
    val currency     : String        = "RWF",
    // FIX: was Long — backend wants a String (e.g. "3")
    val duration     : String        = "1",
    val category     : String        = "",
    val skills       : List<String>  = emptyList(),
    // ── New fields aligned to backend schema ─────────────
    val isUrgent     : Boolean       = false,
    val requiredHours: String        = "0",
    val maxApplicants: String        = "0",
    // ISO-8601 deadline: "2026-05-01" — empty = no deadline
    val deadline     : String        = ""
)

@HiltViewModel
class PostJobViewModel @Inject constructor(
    private val jobRepository: JobRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostJobUiState())
    val uiState: StateFlow<PostJobUiState> = _uiState.asStateFlow()

    // ══════════════════════════════════════════════════════
    // LOAD JOB (edit mode)
    // ══════════════════════════════════════════════════════

    fun loadJob(jobId: String) {
        viewModelScope.launch {
            jobRepository.getJobById(jobId).collect { result ->
                if (result is AppResult.Success) {
                    val job = result.data
                    _uiState.update {
                        it.copy(
                            title         = job.title,
                            description   = job.description,
                            location      = job.location,
                            payment       = job.payment.toString(),
                            currency      = job.currency,
                            // FIX: duration is now String end-to-end
                            duration      = job.duration.toString(),
                            category      = job.category,
                            skills        = job.requiredSkills
                        )
                    }
                }
            }
        }
    }

    // ══════════════════════════════════════════════════════
    // SUBMIT JOB (post or update)
    //
    // BREAKING CHANGES vs old signature:
    //   • duration: Long  → duration: String  (matches backend)
    //   • isUrgent, requiredHours, maxApplicants, deadline added
    //
    // ⚠️  Also update JobRepository.postJob() and updateJob()
    //     to accept duration: String and the new parameters.
    // ══════════════════════════════════════════════════════

    fun submitJob(
        title         : String,
        description   : String,
        location      : String,
        payment       : Double,
        currency      : String,
        // FIX: String — was Long, backend key is "duration" not "duration_days"
        duration      : String,
        category      : String,
        skills        : List<String>,
        isUrgent      : Boolean  = false,
        requiredHours : Int      = 0,
        maxApplicants : Int      = 0,
        // Null = no deadline sent; non-null = ISO-8601 date string
        deadline      : String?  = null,
        editJobId     : String?  = null
    ) {
        if (!validate(title, description, location, payment, category)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                if (editJobId != null) {
                    jobRepository.updateJob(
                        jobId         = editJobId,
                        title         = title,
                        description   = description,
                        location      = location,
                        payment       = payment,
                        currency      = currency,
                        duration      = duration,
                        category      = category,
                        skills        = skills,
                        isUrgent      = isUrgent,
                        requiredHours = requiredHours,
                        maxApplicants = maxApplicants,
                        deadline      = deadline
                    )
                } else {
                    jobRepository.postJob(
                        title         = title,
                        description   = description,
                        location      = location,
                        payment       = payment,
                        currency      = currency,
                        duration      = duration,
                        category      = category,
                        skills        = skills,
                        isUrgent      = isUrgent,
                        requiredHours = requiredHours,
                        maxApplicants = maxApplicants,
                        deadline      = deadline
                    )
                }
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Failed to save job")
                }
            }
        }
    }

    // ── Simple client-side validation ─────────────────────
    private fun validate(
        title       : String,
        description : String,
        location    : String,
        payment     : Double,
        category    : String
    ): Boolean {
        val error = when {
            title.isBlank()       -> "Job title is required"
            description.isBlank() -> "Description is required"
            location.isBlank()    -> "Location is required"
            payment <= 0          -> "Enter a valid payment amount"
            category.isBlank()    -> "Please select a category"
            else                  -> null
        }
        if (error != null) {
            _uiState.update { it.copy(error = error) }
        }
        return error == null
    }
}