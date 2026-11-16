package com.microcareer.match.data.remote.api

import com.microcareer.match.data.remote.dto.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface MicroCareerApiService {

    // =====================================================
    // 🔐 AUTH (Handled by Firebase — Backend no longer used)
    // =====================================================
    // Removed:
    // login
    // register
    // refreshToken
    // logout
    // These are now handled entirely by Firebase SDK.


    // =====================================================
    // 👤 USER PROFILE (Protected)
    // =====================================================

    @GET("users/me")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): Response<ApiResponse<UserDto>>

    @PUT("users/me")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body user: UserDto
    ): Response<ApiResponse<UserDto>>

    @Multipart
    @POST("users/me/profile-picture")
    suspend fun uploadProfilePicture(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Response<ApiResponse<String>>


    // =====================================================
    // 💼 JOBS (Protected)
    // =====================================================

    @GET("jobs")
    suspend fun getJobs(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("category") category: String? = null,
        @Query("lat") latitude: Double? = null,
        @Query("lng") longitude: Double? = null,
        @Query("radius") radius: Int? = null
    ): Response<ApiResponse<PaginatedResponse<JobDto>>>

    @GET("jobs/{id}")
    suspend fun getJob(
        @Header("Authorization") token: String,
        @Path("id") jobId: String
    ): Response<ApiResponse<JobDto>>

    @GET("jobs/recommendations")
    suspend fun getRecommendedJobs(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<JobDto>>>

    @POST("jobs/{id}/apply")
    suspend fun applyForJob(
        @Header("Authorization") token: String,
        @Path("id") jobId: String,
        @Body application: JobApplication
    ): Response<ApiResponse<Unit>>

    @POST("jobs/{id}/save")
    suspend fun saveJob(
        @Header("Authorization") token: String,
        @Path("id") jobId: String
    ): Response<ApiResponse<Unit>>

    @DELETE("jobs/{id}/save")
    suspend fun unsaveJob(
        @Header("Authorization") token: String,
        @Path("id") jobId: String
    ): Response<ApiResponse<Unit>>


    // =====================================================
    // 🧠 SKILLS (Protected)
    // =====================================================

    @POST("extract-skills")
    suspend fun inferSkills(
        @Header("Authorization") token: String,
        @Body request: SkillInputRequest
    ): Response<ApiResponse<SkillInferenceResponse>>

    @Multipart
    @POST("skills/voice-upload")
    suspend fun uploadVoiceNote(
        @Header("Authorization") token: String,
        @Part audio: MultipartBody.Part
    ): Response<ApiResponse<SkillInferenceResponse>>

    @POST("add-skills")
    suspend fun verifySkill(
        @Header("Authorization") token: String,
        @Body skills: List<String>
    ): Response<ApiResponse<Unit>>


    // =====================================================
    // 📚 LEARNING (Protected)
    // =====================================================

    @GET("learning/modules")
    suspend fun getLearningModules(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<LearningModuleDto>>>

    @GET("learning/recommendations")
    suspend fun getRecommendedLearning(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<LearningModuleDto>>>

    @POST("learning/progress")
    suspend fun updateLearningProgress(
        @Header("Authorization") token: String,
        @Body progress: LearningProgress
    ): Response<ApiResponse<Unit>>


    // =====================================================
    // 🔄 SYNC (Protected)
    // =====================================================

    @POST("sync")
    suspend fun syncData(
        @Header("Authorization") token: String,
        @Body syncRequest: SyncRequest
    ): Response<ApiResponse<SyncResponse>>


    // =====================================================
    // 🔔 NOTIFICATIONS (Protected)
    // =====================================================

    @POST("notifications/token")
    suspend fun registerFcmToken(
        @Header("Authorization") Token: String,
        @Body fcmToken: String
    ): Response<ApiResponse<Unit>>
}
