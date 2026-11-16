// ══════════════════════════════════════════════════════════
// CORRECTED TokenManager - Matches YOUR Model
// ══════════════════════════════════════════════════════════
// Location: app/src/main/java/com/microcareer/match/data/local/TokenManager.kt
//
// REPLACE your current TokenManager.kt
// ══════════════════════════════════════════════════════════

package com.microcareer.match.data.local

import android.content.Context
import android.content.SharedPreferences


class TokenManager(context: Context) {


    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val PREFS_NAME = "microcareer_auth_prefs"
        private const val KEY_FCM_TOKEN = "fcm_token"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_TOKEN_TYPE = "token_type"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_FULL_NAME = "full_name"          // ✅ CORRECTED
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_ROLE = "user_role"
        private const val KEY_PHONE_NUMBER = "phone_number"    // ✅ CORRECTED
        private const val KEY_COMPANY_NAME = "company_name"    // ✅ ADDED
    }

    // ══════════════════════════════════════════════════════
    // TOKEN MANAGEMENT
    // ══════════════════════════════════════════════════════

    fun saveAccessToken(token: String, tokenType: String = "Bearer") {
        prefs.edit().apply {
            putString(KEY_ACCESS_TOKEN, token)
            putString(KEY_TOKEN_TYPE, tokenType)
            apply()
        }
    }

    fun getAccessToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null)
    }

    fun getTokenType(): String {
        return prefs.getString(KEY_TOKEN_TYPE, "Bearer") ?: "Bearer"
    }

    fun getAuthorizationHeader(): String? {
        val token = getAccessToken() ?: return null
        val type = getTokenType().replaceFirstChar { it.uppercase() }  // "bearer" → "Bearer"
        return "$type $token"
    }

    fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }

    // ══════════════════════════════════════════════════════
    // USER INFO - CORRECTED FIELD NAMES
    // ══════════════════════════════════════════════════════

    fun saveUserInfo(
        userId: String,
        fullName: String,          // ✅ CORRECTED
        email: String,
        role: String,
        phoneNumber: String? = null,  // ✅ CORRECTED
        companyName: String? = null   // ✅ ADDED
    ) {
        prefs.edit().apply {
            putString(KEY_USER_ID, userId)
            putString(KEY_FULL_NAME, fullName)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_ROLE, role)
            phoneNumber?.let { putString(KEY_PHONE_NUMBER, it) }
            companyName?.let { putString(KEY_COMPANY_NAME, it) }
            apply()
        }
    }

    fun getUserId(): String? {
        return prefs.getString(KEY_USER_ID, null)
    }

    fun getFullName(): String? {          // ✅ CORRECTED
        return prefs.getString(KEY_FULL_NAME, null)
    }

    fun getUserEmail(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    fun getUserRole(): String? {
        return prefs.getString(KEY_USER_ROLE, null)
    }

    fun getPhoneNumber(): String? {      // ✅ CORRECTED
        return prefs.getString(KEY_PHONE_NUMBER, null)
    }

    fun getCompanyName(): String? {      // ✅ ADDED
        return prefs.getString(KEY_COMPANY_NAME, null)
    }

    // ══════════════════════════════════════════════════════
    // ROLE CHECKS
    // ══════════════════════════════════════════════════════

    fun isEmployee(): Boolean {
        return getUserRole() == "EMPLOYEE"
    }

    fun isEmployer(): Boolean {
        return getUserRole() == "EMPLOYER"
    }

    // ══════════════════════════════════════════════════════
    // LOGOUT
    // ══════════════════════════════════════════════════════

    fun clearAuth() {
        prefs.edit().clear().apply()
    }
    fun saveFcmToken(token: String) {
        prefs.edit().putString(KEY_FCM_TOKEN, token).apply()
    }

    fun getFcmToken(): String? {
        return prefs.getString(KEY_FCM_TOKEN, null)
    }
    fun clearFcmToken() {
        prefs.edit().remove(KEY_FCM_TOKEN).apply()
    }

    // Add to TokenManager companion object:

// ─────────────────────────────────────────────────────────
// ADD THIS to your existing TokenManager.kt
// ─────────────────────────────────────────────────────────

// In saveUserInfo(), add companyName to the prefs write:
//   prefs.edit()
//       .putString(KEY_COMPANY_NAME, companyName)
//       ...
//       .apply()

    // Then add this getter:

// ─────────────────────────────────────────────────────────
// ADD THIS to your existing TokenManager KEY constants:
// ─────────────────────────────────────────────────────────
// private const val KEY_COMPANY_NAME = "company_name"
}