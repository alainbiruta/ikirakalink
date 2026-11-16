package com.microcareer.match.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microcareer.match.data.local.entity.UserEntity
import com.microcareer.match.data.repository.AuthRepository
import com.microcareer.match.data.repository.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading : Boolean     = false,
    val isLoggedIn: Boolean     = false,
    val user      : UserEntity? = null,
    val error     : String?     = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        val loggedIn = authRepository.isLoggedIn()
        _uiState.update { it.copy(isLoggedIn = loggedIn) }

        if (loggedIn) {
            viewModelScope.launch {
                authRepository.getCurrentUser().collect { user ->
                    _uiState.update { it.copy(user = user) }
                }
            }
        }
    }

    // ── LOGIN ─────────────────────────────────────────────────────────────
    // `identifier` can be an email address OR a phone number.
    // We detect which one it is and normalize it before passing to the repo.
    fun login(identifier: String, password: String) {
        val normalized = normalizeIdentifier(identifier)
        viewModelScope.launch {
            authRepository.login(normalized, password).collect { result ->
                when (result) {
                    is AppResult.Loading -> _uiState.update {
                        it.copy(isLoading = true, error = null)
                    }
                    is AppResult.Success -> _uiState.update {
                        it.copy(
                            isLoading  = false,
                            isLoggedIn = true,
                            user       = result.data,
                            error      = null
                        )
                    }
                    is AppResult.Error -> _uiState.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }
    }

    // ── REGISTER ──────────────────────────────────────────────────────────
    fun register(
        email       : String,
        password    : String,
        fullName    : String,
        role        : String,
        phoneNumber : String? = null,
        location    : String? = null,
        companyName : String? = null
    ) {
        viewModelScope.launch {
            authRepository.register(
                email       = email,
                password    = password,
                fullName    = fullName,
                role        = role,
                phoneNumber = phoneNumber,
                location    = location,
                companyName = companyName
            ).collect { result ->
                when (result) {
                    is AppResult.Loading -> _uiState.update {
                        it.copy(isLoading = true, error = null)
                    }
                    is AppResult.Success -> _uiState.update {
                        it.copy(
                            isLoading  = false,
                            isLoggedIn = true,
                            user       = result.data,
                            error      = null
                        )
                    }
                    is AppResult.Error -> _uiState.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }
    }

    // ── LOGOUT ────────────────────────────────────────────────────────────
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.update { AuthUiState() }
        }
    }

    // ── CLEAR ERROR ───────────────────────────────────────────────────────
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    // ── HELPERS ───────────────────────────────────────────────────────────

    /**
     * Detects whether the input is a phone number or email and normalizes it.
     *
     * Phone detection rules:
     *  - starts with "+" (international format, e.g. +250788...)
     *  - starts with "07" or "08" (local Rwandan format)
     *  - contains only digits (and optional leading +)
     *
     * Phone normalization:
     *  - "0788123456" → "+250788123456"  (prepend Rwanda country code)
     *  - "+250788123456" → kept as-is
     *
     * Email: returned trimmed and lowercased.
     */
    private fun normalizeIdentifier(raw: String): String {
        val trimmed = raw.trim()
        return if (isPhoneNumber(trimmed)) normalizePhone(trimmed) else trimmed.lowercase()
    }

    private fun isPhoneNumber(input: String): Boolean {
        // Matches: +2507..., 07..., 08..., or a string of 9-15 digits optionally prefixed with +
        val digitsOnly = input.replace("+", "").replace(" ", "").replace("-", "")
        return input.startsWith("+") ||
                input.startsWith("07") ||
                input.startsWith("08") ||
                digitsOnly.all { it.isDigit() } && digitsOnly.length in 9..15
    }

    private fun normalizePhone(phone: String): String {
        val cleaned = phone.replace(" ", "").replace("-", "")
        return when {
            cleaned.startsWith("+")  -> cleaned               // already international
            cleaned.startsWith("0")  -> "+250${cleaned.drop(1)}" // local → Rwanda intl
            else                     -> "+$cleaned"
        }
    }
}