package com.microcareer.match.data.remote.api

import com.microcareer.match.data.remote.models.*
import retrofit2.Response
import retrofit2.http.*
import com.microcareer.match.data.remote.models.UpdateProfileRequest
// ══════════════════════════════════════════════════════════
// AUTH SERVICE
// ══════════════════════════════════════════════════════════

interface AuthApiService {

    @POST("api/v1/auth/token")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @POST("api/v1/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthResponse>

    @GET("api/v1/users/me")
    suspend fun getCurrentUser(): Response<RemoteUser>

    // ── FCM token registration ─────────────────────────────
    // Called after login/register so the backend can push notifications
    // to this device. Authorization header is set by OkHttp interceptor.
    @POST("api/v1/auth/fcm-token")
    suspend fun updateFcmToken(
        @Body body: FcmTokenRequest
    ): Response<Unit>
    @PUT("api/v1/users/me/skills")
    suspend fun updateSkills(
        @Body body: SkillsUpdateRequest
    ): Response<ApiResponse<Any>>

    @PUT("api/v1/users/me")
    suspend fun updateProfile(
        @Body body: UpdateProfileRequest
    ): Response<ApiResponse<Any>>
}

// ══════════════════════════════════════════════════════════
// JOBS SERVICE
// ══════════════════════════════════════════════════════════

interface JobsApiService {

    // ── Employee: browse all jobs ──────────────────────────
    @GET("api/v1/jobs")
    suspend fun getJobs(
        @Query("skip")        skip        : Int     = 0,
        @Query("limit")       limit       : Int     = 20,
        @Query("category")    category    : String? = null,
        @Query("location")    location    : String? = null,
        @Query("min_payment") minPayment  : Double? = null,
        @Query("max_payment") maxPayment  : Double? = null
    ): Response<JobsResponse>

    // ── Employee: single job ───────────────────────────────
    @GET("api/v1/jobs/{id}")
    suspend fun getJob(
        @Path("id") jobId: String
    ): Response<ApiResponse<RemoteJob>>

    // ── Employee: AI recommendations ──────────────────────
    @GET("api/v1/matching/recommendations/advanced")
    suspend fun getRecommendations(
        @Query("limit")     limit    : Int    = 10,
        @Query("min_score") minScore : Double = 0.1
    ): Response<ApiResponse<Map<String, Any>>>

    // ── Employee: search ──────────────────────────────────
    @GET("api/v1/jobs/search")
    suspend fun searchJobs(
        @Query("q")        query    : String,
        @Query("category") category : String? = null,
        @Query("location") location : String? = null,
        @Query("skip")     skip     : Int     = 0,
        @Query("limit")    limit    : Int     = 20
    ): Response<JobsResponse>

    // ── Employer: get own job listings ────────────────────
    @GET("api/v1/employer/jobs/mine")
    suspend fun getEmployerJobs(
        @Query("page")      page     : Int = 1,
        @Query("page_size") pageSize : Int = 20
    ): Response<JobListApiResponse>

    // ── Employer: post new job ────────────────────────────
    @POST("api/v1/employer/jobs")
    suspend fun postJob(
        @Body request: PostJobRequest
    ): Response<ApiResponse<RemoteJob>>

    // ── Employer: edit existing job ───────────────────────
    @PUT("api/v1/employer/jobs/{jobId}")
    suspend fun updateJob(
        @Path("jobId") jobId    : String,
        @Body          request  : PostJobRequest
    ): Response<RemoteJob>

    // ── Employer: delete job ──────────────────────────────
    @DELETE("api/v1/employer/jobs/{jobId}")
    suspend fun deleteJob(
        @Path("jobId") jobId: String
    ): Response<Unit>

    // ── Employer: open / close job ────────────────────────
    @PATCH("api/v1/employer/jobs/{jobId}/status")
    suspend fun updateJobStatus(
        @Path("jobId") jobId : String,
        @Body          body  : Map<String, String>
    ): Response<RemoteJob>
}

// ══════════════════════════════════════════════════════════
// APPLICATIONS SERVICE
// ══════════════════════════════════════════════════════════

interface ApplicationsApiService {

    // ── Employee: submit application ──────────────────────
    @POST("api/v1/jobs/{jobId}/apply")
    suspend fun createApplication(
        @Path("jobId") jobId: String,
        @Body request: ApplicationRequest        // new model below
    ): Response<ApiResponse<Any>>

    // ── Employee: my submitted applications ───────────────
    @GET("api/v1/applications/")
    suspend fun getMyApplications(
        @Query("page")      page     : Int    = 1,
        @Query("page_size") pageSize : Int    = 20,
        @Query("status")    status   : String? = null
    ): Response<ApiResponse<Any>>

    // ── Employer: applications received for their jobs ────
    @GET("api/v1/applications/received")
    suspend fun getReceivedApplications():  Response<ReceivedApplicationsResponse>

    // ── Employer: accept or reject an applicant ───────────
    @PATCH("api/v1/applications/{applicantId}/status")
    suspend fun updateApplicationStatus(
        @Path("applicantId") applicantId : String,
        @Query("job_id")     jobId       : String,
        @Query("status")     status      : String
    ): Response<Unit>
}