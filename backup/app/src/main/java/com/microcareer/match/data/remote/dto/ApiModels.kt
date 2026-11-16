package com.microcareer.match.data.remote.dto

import com.google.gson.annotations.SerializedName

// User DTOs
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String?
)

data class AuthResponse(
    val token: String,
    val refreshToken: String,
    val user: UserDto
)

data class UserDto(
    val id: String,
    val email: String,
    val fullName: String,
    val phoneNumber: String?,
    val profilePictureUrl: String?,
    val location: String?,
    val latitude: Double?,
    val longitude: Double?,
    val rating: Float,
    val completedJobs: Int,
    val skills: List<String>,
    val createdAt: Long,
    val updatedAt: Long
)

// Job DTOs
data class JobDto(
    val id: String,
    val title: String,
    val description: String,
    val employerId: String,
    val employerName: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val requiredSkills: List<String>,
    val payment: Double,
    val currency: String,
    val duration: String,
    val category: String,
    val matchScore: Float,
    val status: String,
    val deadline: Long?,
    val createdAt: Long,
    val updatedAt: Long
)

data class JobApplication(
    val jobId: String,
    val coverLetter: String? = null
)

// Skill DTOs
data class SkillInputRequest(
    val input: String,
    val inputType: String, // TEXT, VOICE, QUIZ
    val audioUrl: String? = null
)

data class SkillInferenceResponse(
    val inferredSkills: List<InferredSkill>,
    val confidence: Float
)

data class InferredSkill(
    val skillName: String,
    val proficiencyLevel: String,
    val confidence: Float
)

// Learning Module DTOs

data class LearningProgress(
    val moduleId: String,
    val progress: Int,
    val isCompleted: Boolean,
    val completedAt: Long?
)

// Generic API Response
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String?,
    val error: String?
)

data class PaginatedResponse<T>(
    val items: List<T>,
    val total: Int,
    val page: Int,
    val pageSize: Int,
    val hasMore: Boolean
)

// Sync DTOs
data class SyncRequest(
    val unsyncedJobs: List<String>,
    val unsyncedLearning: List<String>,
    val unsyncedSkills: List<SkillProfileSync>,
    val lastSyncTimestamp: Long
)

data class SkillProfileSync(
    val skillName: String,
    val proficiencyLevel: String,
    val sourceType: String,
    val rawInput: String?
)

data class SyncResponse(
    val newJobs: List<JobDto>,
    val newLearningModules: List<LearningModuleDto>,
    val syncTimestamp: Long
)
