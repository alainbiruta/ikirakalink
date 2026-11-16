package com.microcareer.match.presentation.profile

import com.microcareer.match.utils.LocaleHelper

// ─────────────────────────────────────────────────────────
// UI State
// ─────────────────────────────────────────────────────────

data class ProfileUiState(
    val isLoading            : Boolean      = false,
    val isUploadingPhoto     : Boolean      = false,
    val user                 : UserUi?      = null,
    val fullName             : String?      = null,
    val bio                  : String?      = null,
    val profilePhotoUrl      : String?      = null,
    val phoneNumber          : String?      = null,
    val location             : String?      = null,
    val rating               : Float        = 0f,
    val completedJobs        : Int          = 0,
    val totalReviews         : Int          = 0,
    val skills               : List<String> = emptyList(),
    val notificationsEnabled : Boolean      = true,
    val selectedLanguage     : String       = LocaleHelper.supportedLanguages
        .entries
        .firstOrNull { it.value == LocaleHelper.DEFAULT_LOCALE }
        ?.key ?: "Ikinyarwanda",
    val error                : String?      = null
)

// ─────────────────────────────────────────────────────────
// Minimal UI model (avoids exposing full entity to UI layer)
// ─────────────────────────────────────────────────────────

data class UserUi(
    val id    : String,
    val name  : String,
    val email : String
)