package com.microcareer.match.presentation.home

sealed class JobsState {
    data object Loading : JobsState()
    data object Empty   : JobsState()

    data class Error(
        val message: String
    ) : JobsState()

    data class Success(
        val jobs: List<JobUiModel>
    ) : JobsState()
}