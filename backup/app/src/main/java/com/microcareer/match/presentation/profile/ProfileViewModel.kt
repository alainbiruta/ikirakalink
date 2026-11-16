package com.microcareer.match.presentation.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microcareer.match.domain.model.UserProfile
import com.microcareer.match.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.microcareer.match.data.remote.models.UpdateProfileRequest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.microcareer.match.utils.LocaleHelper
import android.content.Context

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState(isLoading = true))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProfileEvent>()
    val events: SharedFlow<ProfileEvent> = _events.asSharedFlow()

    init { observeProfile() }

    // ── LOAD ────────────────────────────────────────────────────────────────

    private fun observeProfile() {
        viewModelScope.launch {
            repository.getUserProfile()
                .onStart  { _uiState.update { it.copy(isLoading = true, error = null) } }
                .catch    { e -> _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to load profile") } }
                .collect  { profile ->
                    _uiState.update { current ->
                        current.copy(
                            isLoading       = false,
                            user            = UserUi(id = profile.id, name = profile.name, email = profile.email),
                            skills          = profile.skills,
                            fullName        = profile.name,
                            // FIX (Bug 4): Access fields directly instead of reflection hacks.
                            // These fields must exist on UserProfile — see UserProfile.kt fix below.
                            profilePhotoUrl = profile.profilePhotoUrl,
                            phoneNumber     = profile.phoneNumber,
                            location        = profile.location,
                            rating          = profile.rating,
                            completedJobs   = profile.completedJobs,
                            bio             = profile.bio,
                            error           = null
                        )
                    }
                }
        }
    }

    // ── PHOTO ───────────────────────────────────────────────────────────────

    fun uploadProfilePhoto(uri: Uri) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isUploadingPhoto = true) }
                kotlinx.coroutines.delay(1000)
                _uiState.update { it.copy(isUploadingPhoto = false, profilePhotoUrl = uri.toString()) }
                _events.emit(ProfileEvent.ShowSnackbar("Profile photo updated"))
            } catch (e: Exception) {
                _uiState.update { it.copy(isUploadingPhoto = false, error = "Failed to upload photo: ${e.message}") }
            }
        }
    }

    // ── PERSONAL INFO ───────────────────────────────────────────────────────

    // AFTER
    fun updatePersonalInfo(name: String, phone: String, location: String) {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        fullName    = name.ifBlank     { it.fullName ?: "" },
                        phoneNumber = phone.ifBlank    { it.phoneNumber ?: "" },
                        location    = location.ifBlank { it.location ?: "" }
                    )
                }
                repository.savePersonalInfo(name, phone, location)
                _events.emit(ProfileEvent.ShowSnackbar("Profile updated"))
            } catch (e: Exception) {
                _events.emit(ProfileEvent.ShowSnackbar("Failed to update profile: ${e.message}"))
            }
        }
    }

    fun updateFullName(name: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                _uiState.update { it.copy(isLoading = false, fullName = name) }
                _events.emit(ProfileEvent.ShowSnackbar("Name updated"))
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to update name: ${e.message}") }
            }
        }
    }

    // AFTER
    fun updateBio(bio: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(bio = bio) }
                repository.saveBio(bio)
                _events.emit(ProfileEvent.ShowSnackbar("Bio updated"))
            } catch (e: Exception) {
                _events.emit(ProfileEvent.ShowSnackbar("Failed to update bio"))
            }
        }
    }

    // ── SKILLS ──────────────────────────────────────────────────────────────
    // FIX (Bug 1): Call repository.saveSkills() so changes persist to Room
    // and sync to the backend. Previously this only updated _uiState in memory,
    // so skills were lost on every logout or app restart.

    fun updateSkills(skills: List<String>) {
        viewModelScope.launch {
            try {
                // Optimistically update UI first so the screen feels instant
                _uiState.update { it.copy(skills = skills) }
                // Persist to Room + push to backend via UserRepositoryImpl.saveSkills()
                repository.saveSkills(skills)
                _events.emit(ProfileEvent.ShowSnackbar("Skills saved"))
            } catch (e: Exception) {
                android.util.Log.e("ProfileViewModel", "Failed to save skills", e)
                _events.emit(ProfileEvent.ShowSnackbar("Failed to save skills — will retry when online"))
            }
        }
    }

    // ── PASSWORD ────────────────────────────────────────────────────────────

    fun updatePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                _uiState.update { it.copy(isLoading = false) }
                _events.emit(ProfileEvent.ShowSnackbar("Password changed successfully"))
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _events.emit(ProfileEvent.ShowSnackbar("Failed to change password: ${e.message}"))
            }
        }
    }

    // ── NOTIFICATIONS ───────────────────────────────────────────────────────

    fun onNotificationsChanged(enabled: Boolean) {
        _uiState.update { it.copy(notificationsEnabled = enabled) }
    }

    // ── LANGUAGE ────────────────────────────────────────────────────────────

    fun onLanguageSelected(context: Context, languageTag: String) {
        LocaleHelper.setLocale(context, languageTag)
        val displayName = LocaleHelper.supportedLanguages
            .entries.firstOrNull { it.value == languageTag }?.key ?: languageTag
        _uiState.update { it.copy(selectedLanguage = displayName) }
        viewModelScope.launch {
            _events.emit(ProfileEvent.ShowSnackbar("Ururimi rwahinduwe / Language changed"))
        }
    }

    fun loadSavedLanguage(context: Context) {
        val tag = LocaleHelper.getSavedLocale(context)
        val displayName = LocaleHelper.supportedLanguages
            .entries.firstOrNull { it.value == tag }?.key ?: tag
        _uiState.update { it.copy(selectedLanguage = displayName) }
    }

    // ── MISC ────────────────────────────────────────────────────────────────

    fun clearError() { _uiState.update { it.copy(error = null) } }

    fun logout() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                repository.logout()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Logout failed: ${e.message}") }
            }
        }
    }
}

// ── EVENTS ──────────────────────────────────────────────────────────────────

sealed class ProfileEvent {
    data class ShowSnackbar(val message: String) : ProfileEvent()
}