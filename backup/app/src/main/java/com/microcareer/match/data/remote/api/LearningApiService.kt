package com.microcareer.match.data.remote.api

import com.microcareer.match.data.remote.models.ApiResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit interface for /api/v1/learning endpoints.
 *
 * Routes match api/v1/learning.py exactly:
 *   GET  /                    → browse all modules
 *   GET  /recommendations/me  → skill-gap recommendations
 *   GET  /progress/me         → user's progress summary
 *   GET  /{moduleId}          → single module detail
 *   POST /{moduleId}/start    → mark in-progress
 *   POST /{moduleId}/progress → update progress %
 *   POST /{moduleId}/complete → mark complete + unlock skills
 *
 * Authorization header is added by OkHttp interceptor — no
 * @Header("Authorization") needed on individual calls.
 */
interface LearningApiService {

    // ── Browse ─────────────────────────────────────────────
    @GET("api/v1/learning/")
    suspend fun getModules(
        @Query("page")         page        : Int     = 1,
        @Query("page_size")    pageSize    : Int     = 50,
        @Query("skill_name")   skillName   : String? = null,
        @Query("difficulty")   difficulty  : String? = null,
        @Query("content_type") contentType : String? = null
    ): Response<ApiResponse<Any>>

    // ── Personalised recommendations ───────────────────────
    @GET("api/v1/learning/recommendations/me")
    suspend fun getRecommendations(
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<Any>>

    // ── Progress summary ───────────────────────────────────
    @GET("api/v1/learning/progress/me")
    suspend fun getMyProgress(): Response<ApiResponse<Any>>

    // ── Single module ──────────────────────────────────────
    @GET("api/v1/learning/{moduleId}")
    suspend fun getModule(
        @Path("moduleId") moduleId: String
    ): Response<ApiResponse<Any>>

    // ── Start module ───────────────────────────────────────
    @POST("api/v1/learning/{moduleId}/start")
    suspend fun startModule(
        @Path("moduleId") moduleId: String
    ): Response<ApiResponse<Any>>

    // ── Update progress ────────────────────────────────────
    @POST("api/v1/learning/{moduleId}/progress")
    suspend fun updateProgress(
        @Path("moduleId")        moduleId        : String,
        @Query("progress_percent") progressPercent : Int
    ): Response<ApiResponse<Any>>

    // ── Complete module (unlocks skills on user profile) ───
    @POST("api/v1/learning/{moduleId}/complete")
    suspend fun completeModule(
        @Path("moduleId") moduleId: String
    ): Response<ApiResponse<Any>>
}