package com.microcareer.match.presentation.job

sealed class JobUiEvent {
    data class ShowSnackbar(val message: String) : JobUiEvent()
    data object ApplySuccess : JobUiEvent()
}