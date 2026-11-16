package com.microcareer.match.presentation.home

data class JobUiModel(
    val id          : String,
    val title       : String,
    val company     : String,
    val location    : String,
    val category    : String,
    val salary      : String,
    val description : String,
    val matchScore  : Float   = 0f,
    val isSaved     : Boolean = false,
    val isApplying  : Boolean = false,
    val isApplied   : Boolean = false
)