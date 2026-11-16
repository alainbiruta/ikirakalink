// ══════════════════════════════════════════════════════════
// API Models for Authentication
// Location: app/src/main/java/com/microcareer/app/data/models/
// ══════════════════════════════════════════════════════════

package com.microcareer.match.data.models

import com.google.gson.annotations.SerializedName

// ════════════════════════════════════════════════════════════
// AUTH REQUEST MODELS
// ════════════════════════════════════════════════════════════

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String = "EMPLOYEE", // or "EMPLOYER"
    val phone: String? = null
)

// ════════════════════════════════════════════════════════════
// AUTH RESPONSE MODELS
// ════════════════════════════════════════════════════════════

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val data: AuthData?
)

data class AuthData(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String = "bearer",

    val user: User
)

// ════════════════════════════════════════════════════════════
// USER MODEL
// ════════════════════════════════════════════════════════════

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val phone: String?,

    @SerializedName("is_active")
    val isActive: Boolean,

    @SerializedName("is_verified")
    val isVerified: Boolean,

    val skills: List<String>?,
    val bio: String?,
    val location: String?,

    @SerializedName("profile_image")
    val profileImage: String?,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)

// ════════════════════════════════════════════════════════════
// GENERIC API RESPONSE
// ════════════════════════════════════════════════════════════

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)

// ════════════════════════════════════════════════════════════
// ERROR RESPONSE
// ════════════════════════════════════════════════════════════

data class ErrorResponse(
    val detail: String
)