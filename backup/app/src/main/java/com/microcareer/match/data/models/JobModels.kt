package com.microcareer.match.data.models

import com.google.gson.annotations.SerializedName

// ════════════════════════════════════════════════════════════
// JOB MODEL
// ════════════════════════════════════════════════════════════

data class Job(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val location: String,
    val payment: Double,
    val currency: String = "RWF",

    @SerializedName("payment_type")
    val paymentType: String, // FIXED, HOURLY, NEGOTIABLE

    @SerializedName("required_skills")
    val requiredSkills: List<String>?,

    @SerializedName("preferred_skills")
    val preferredSkills: List<String>?,

    val duration: String?,

    @SerializedName("start_date")
    val startDate: String?,

    val urgency: String?, // LOW, MEDIUM, HIGH
    val status: String, // OPEN, CLOSED, CANCELLED

    @SerializedName("employer_id")
    val employerId: String,

    @SerializedName("employer_name")
    val employerName: String?,

    @SerializedName("application_count")
    val applicationCount: Int = 0,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    // For matching (optional fields from recommendations endpoint)
    @SerializedName("match_score")
    val matchScore: Double? = null,

    @SerializedName("match_breakdown")
    val matchBreakdown: MatchBreakdown? = null,

    val recommendation: String? = null
)

data class MatchBreakdown(
    val skills: Double,
    val location: Double,
    val payment: Double,
    val experience: Double,
    val category: Double
)

// ════════════════════════════════════════════════════════════
// JOB REQUEST MODELS
// ════════════════════════════════════════════════════════════

data class CreateJobRequest(
    val title: String,
    val description: String,
    val category: String,
    val location: String,
    val payment: Double,

    @SerializedName("payment_type")
    val paymentType: String,

    @SerializedName("required_skills")
    val requiredSkills: List<String>,

    @SerializedName("preferred_skills")
    val preferredSkills: List<String>? = null,

    val duration: String? = null,

    @SerializedName("start_date")
    val startDate: String? = null,

    val urgency: String? = "MEDIUM"
)

// ════════════════════════════════════════════════════════════
// JOB LIST RESPONSE
// ════════════════════════════════════════════════════════════

data class JobListResponse(
    val success: Boolean,
    val message: String,
    val data: JobListData?
)

data class JobListData(
    val jobs: List<Job>,
    val total: Int,
    val skip: Int = 0,
    val limit: Int = 10
)

// ════════════════════════════════════════════════════════════
// JOB RECOMMENDATIONS RESPONSE
// ════════════════════════════════════════════════════════════

data class JobRecommendationsResponse(
    val success: Boolean,
    val message: String,
    val data: RecommendationsData?
)

data class RecommendationsData(
    val recommendations: List<Job>,
    val total: Int,
    val algorithm: String,
    val factors: List<String>
)
