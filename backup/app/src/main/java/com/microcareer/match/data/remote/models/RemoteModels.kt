package com.microcareer.match.data.remote.models

import com.google.gson.annotations.SerializedName

// ══════════════════════════════════════════════════════════
// AUTHENTICATION
// ══════════════════════════════════════════════════════════

data class LoginRequest(
    val username: String,
    val password: String
)
data class FcmTokenRequest(
    @SerializedName("fcm_token") val fcmToken: String
)
data class RegisterRequest(
    @SerializedName("email")        val email       : String,
    @SerializedName("password")     val password    : String,
    @SerializedName("full_name")    val fullName    : String,
    @SerializedName("role")         val role        : String,
    @SerializedName("phone_number") val phoneNumber : String? = null,
    @SerializedName("location")     val location    : String? = null,
    @SerializedName("company_name") val companyName : String? = null
)

data class AuthResponse(
    @SerializedName("access_token") val accessToken : String,
    @SerializedName("token_type")   val tokenType   : String,
    @SerializedName("user")         val user        : RemoteUser
)

// ══════════════════════════════════════════════════════════
// USER
// ══════════════════════════════════════════════════════════

data class RemoteUser(
    @SerializedName("id")                  val id               : String,
    @SerializedName("email")               val email            : String,
    @SerializedName("full_name")           val fullName         : String,
    @SerializedName("role")                val role             : String,
    @SerializedName("phone_number")        val phoneNumber      : String? = null,
    @SerializedName("profile_picture_url") val profilePictureUrl: String? = null,
    @SerializedName("location")            val location         : String? = null,
    @SerializedName("skills")              val skills           : List<String>? = null,
    @SerializedName("available_hours")     val availableHours   : String? = null,
    @SerializedName("company_name")        val companyName      : String? = null,
    @SerializedName("created_at")          val createdAt        : String,
    @SerializedName("updated_at")          val updatedAt        : String
)

// ══════════════════════════════════════════════════════════
// JOBS
// ══════════════════════════════════════════════════════════
data class JobListApiResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data")    val data: JobListData
)

data class JobListData(
    @SerializedName("jobs")      val jobs     : List<RemoteJob>,
    @SerializedName("total")     val total    : Int,
    @SerializedName("page")      val page     : Int,
    @SerializedName("page_size") val pageSize : Int,
    @SerializedName("has_more")  val hasMore  : Boolean
)

data class RemoteJob(
    @SerializedName("id")               val id              : String,
    @SerializedName("title")            val title           : String,
    @SerializedName("description")      val description     : String,
    @SerializedName("category")         val category        : String,
    @SerializedName("location")         val location        : String,
    @SerializedName("payment")          val payment         : Double,
    @SerializedName("currency")         val currency        : String = "RWF",
    @SerializedName("payment_type")     val paymentType     : String,
    @SerializedName("required_skills")  val requiredSkills  : List<String>,
    @SerializedName("experience_level") val experienceLevel : String? = null,
    @SerializedName("duration_days")    val durationDays    : Int? = null,
    @SerializedName("duration")      val duration        : String? = null,
    @SerializedName("status")           val status          : String,
    @SerializedName("employer_id")      val employerId      : String,
    @SerializedName("employer_name")    val employerName    : String? = null,
    @SerializedName("created_at")       val createdAt       : String,
    @SerializedName("updated_at")       val updatedAt       : String,
    @SerializedName("match_score")      val matchScore      : Double? = null,
    @SerializedName("has_applied") val hasApplied: Boolean = false,
    @SerializedName("recommendation")   val recommendation  : String? = null
)

data class CreateJobRequest(
    @SerializedName("title")            val title           : String,
    @SerializedName("description")      val description     : String,
    @SerializedName("category")         val category        : String,
    @SerializedName("location")         val location        : String,
    @SerializedName("payment")          val payment         : Double,
    @SerializedName("currency")         val currency        : String = "RWF",
    @SerializedName("payment_type")     val paymentType     : String,
    @SerializedName("required_skills")  val requiredSkills  : List<String>,
    @SerializedName("experience_level") val experienceLevel : String? = null,
    @SerializedName("duration_days")    val durationDays    : Int? = null
)
data class ReceivedApplicationsResponse(
    @SerializedName("success")  val success: Boolean,
    @SerializedName("message")  val message: String,
    @SerializedName("data")     val data: ReceivedApplicationsData
)

data class ReceivedApplicationsData(
    @SerializedName("applications") val applications: List<RemoteApplication>,
    @SerializedName("total")        val total: Int,
    @SerializedName("page")         val page: Int?       = null,
    @SerializedName("page_size")    val pageSize: Int?   = null,
    @SerializedName("has_more")     val hasMore: Boolean = false
)
data class ApplicationRequest(
    @SerializedName("message")      val message     : String? = null,
    @SerializedName("cover_letter") val coverLetter : String? = null
)
// ══════════════════════════════════════════════════════════
// POST JOB REQUEST  ← Fixed to match backend schema exactly
//
// Key differences from the old version:
//   • "duration_days" (Long) → "duration" (String) — backend field name + type
//   • Added: latitude, longitude (optional — null skips geo-matching)
//   • Added: is_urgent (Boolean, default false)
//   • Added: required_hours (Int, default 0)
//   • Added: deadline (ISO-8601 String, optional)
//   • Added: max_applicants (Int, default 0 = unlimited)
//   • Added: required_availability (empty map by default)
// ══════════════════════════════════════════════════════════

data class PostJobRequest(
    // ── Core (required) ──────────────────────────────────
    @SerializedName("title")                  val title               : String,
    @SerializedName("description")            val description         : String,
    @SerializedName("location")               val location            : String,
    @SerializedName("payment")                val payment             : Double,
    @SerializedName("currency")               val currency            : String = "RWF",
    // FIX: was "duration_days": Long — backend key is "duration": String
    @SerializedName("duration")               val duration            : String,
    @SerializedName("category")               val category            : String,
    @SerializedName("required_skills")        val requiredSkills      : List<String> = emptyList(),

    // ── Optional location coords ──────────────────────────
    // Send null to skip geo-matching; backend defaults to (-90, -180) if omitted
    @SerializedName("latitude")               val latitude            : Double? = null,
    @SerializedName("longitude")              val longitude           : Double? = null,

    // ── Job options ───────────────────────────────────────
    @SerializedName("is_urgent")              val isUrgent            : Boolean = false,
    @SerializedName("required_hours")  val requiredHours  : Int? = null,

    // ── Scheduling ────────────────────────────────────────
    // ISO-8601 format: "2026-05-01T00:00:00.000Z"
    // Pass null if employer doesn't set a deadline
    @SerializedName("deadline")               val deadline            : String? = null,
    @SerializedName("max_applicants")  val maxApplicants  : Int? = null,

    // ── Availability map ──────────────────────────────────
    // Backend accepts a flexible map of availability windows.
    // Empty map = no specific availability requirement.
    @SerializedName("required_availability")  val requiredAvailability: Map<String, List<String>> = emptyMap()
)

// ══════════════════════════════════════════════════════════
// APPLICATIONS
// ══════════════════════════════════════════════════════════

data class RemoteApplicationEmployee(
    @SerializedName("id")            val id            : String? = null,
    @SerializedName("fullName")      val fullName      : String? = null,
    @SerializedName("phoneNumber")         val phoneNumber       : String? = null,  // ← ADD
    @SerializedName("profilePictureUrl")   val profilePictureUrl : String? = null,  // ← ADD
    @SerializedName("location")      val location      : String? = null,
    @SerializedName("rating")        val rating        : Double? = null,
    @SerializedName("completedJobs") val completedJobs : Int?    = null,
    @SerializedName("skills")        val skills        : List<String>? = null
)

data class RemoteApplicationJob(
    @SerializedName("id")    val id    : String? = null,
    @SerializedName("title") val title : String? = null
)

data class RemoteApplication(
    @SerializedName("id")          val id          : String?                   = null,
    @SerializedName("status")      val status      : String?                   = "PENDING",
    @SerializedName("matchScore")  val matchScore  : Double?                   = null,
    @SerializedName("appliedAt")   val appliedAt   : String?                   = null,
    @SerializedName("message")     val message     : String?                   = null,
    @SerializedName("coverLetter") val coverLetter : String?                   = null,
    @SerializedName("employee")    val employee    : RemoteApplicationEmployee? = null,
    @SerializedName("job")         val job         : RemoteApplicationJob?     = null
) {
    fun toApplicantUiModel() =
        com.microcareer.match.presentation.employer.ApplicantUiModel(
            applicantId = id ?: "",
            applicantName = employee?.fullName ?: "Unknown",
            jobId         = job?.id            ?: "",
            jobTitle      = job?.title         ?: "",
            matchScore    = (matchScore        ?: 0.0).toFloat(),
            skills        = employee?.skills   ?: emptyList(),
            status        = status             ?: "PENDING",
            phoneNumber         = employee?.phoneNumber,       // ← ADD
            profilePictureUrl   = employee?.profilePictureUrl
        )
}
// ══════════════════════════════════════════════════════════
// API RESPONSES
// ══════════════════════════════════════════════════════════

data class ApiResponse<T>(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("data")    val data    : T? = null
)

data class JobsResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("data")    val data    : JobsData
)

data class JobsData(
    @SerializedName("jobs")  val jobs  : List<RemoteJob>,
    @SerializedName("total") val total : Int,
    @SerializedName("skip")  val skip  : Int? = 0,
    @SerializedName("limit") val limit : Int? = 20
)

data class ErrorResponse(
    @SerializedName("detail")  val detail  : String? = null,
    @SerializedName("message") val message : String? = null
)
data class SkillsUpdateRequest(
    @SerializedName("skills") val skills: List<String>
)

// ══════════════════════════════════════════════════════════
// RESULT WRAPPER
// ══════════════════════════════════════════════════════════

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}