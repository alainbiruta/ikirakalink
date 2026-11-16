package com.microcareer.match.data.remote.api;

import com.microcareer.match.data.remote.models.ApiResponse;
import retrofit2.Response;
import retrofit2.http.*;

/**
 * Retrofit interface for /api/v1/learning endpoints.
 *
 * Routes match api/v1/learning.py exactly:
 *  GET  /                    → browse all modules
 *  GET  /recommendations/me  → skill-gap recommendations
 *  GET  /progress/me         → user's progress summary
 *  GET  /{moduleId}          → single module detail
 *  POST /{moduleId}/start    → mark in-progress
 *  POST /{moduleId}/progress → update progress %
 *  POST /{moduleId}/complete → mark complete + unlock skills
 *
 * Authorization header is added by OkHttp interceptor — no
 * @Header("Authorization") needed on individual calls.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000f\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J$\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007JR\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00040\u00032\b\b\u0003\u0010\n\u001a\u00020\u000b2\b\b\u0003\u0010\f\u001a\u00020\u000b2\n\b\u0003\u0010\r\u001a\u0004\u0018\u00010\u00062\n\b\u0003\u0010\u000e\u001a\u0004\u0018\u00010\u00062\n\b\u0003\u0010\u000f\u001a\u0004\u0018\u00010\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0012J$\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00040\u00032\b\b\u0003\u0010\u0014\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0015J$\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J.\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0018\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/microcareer/match/data/remote/api/LearningApiService;", "", "completeModule", "Lretrofit2/Response;", "Lcom/microcareer/match/data/remote/models/ApiResponse;", "moduleId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getModule", "getModules", "page", "", "pageSize", "skillName", "difficulty", "contentType", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMyProgress", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecommendations", "limit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startModule", "updateProgress", "progressPercent", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface LearningApiService {
    
    @retrofit2.http.GET(value = "api/v1/learning/")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getModules(@retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "page_size")
    int pageSize, @retrofit2.http.Query(value = "skill_name")
    @org.jetbrains.annotations.Nullable()
    java.lang.String skillName, @retrofit2.http.Query(value = "difficulty")
    @org.jetbrains.annotations.Nullable()
    java.lang.String difficulty, @retrofit2.http.Query(value = "content_type")
    @org.jetbrains.annotations.Nullable()
    java.lang.String contentType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/learning/recommendations/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRecommendations(@retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/learning/progress/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMyProgress(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/learning/{moduleId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getModule(@retrofit2.http.Path(value = "moduleId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/learning/{moduleId}/start")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startModule(@retrofit2.http.Path(value = "moduleId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/learning/{moduleId}/progress")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateProgress(@retrofit2.http.Path(value = "moduleId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, @retrofit2.http.Query(value = "progress_percent")
    int progressPercent, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/learning/{moduleId}/complete")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object completeModule(@retrofit2.http.Path(value = "moduleId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
    
    /**
     * Retrofit interface for /api/v1/learning endpoints.
     *
     * Routes match api/v1/learning.py exactly:
     *  GET  /                    → browse all modules
     *  GET  /recommendations/me  → skill-gap recommendations
     *  GET  /progress/me         → user's progress summary
     *  GET  /{moduleId}          → single module detail
     *  POST /{moduleId}/start    → mark in-progress
     *  POST /{moduleId}/progress → update progress %
     *  POST /{moduleId}/complete → mark complete + unlock skills
     *
     * Authorization header is added by OkHttp interceptor — no
     * @Header("Authorization") needed on individual calls.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}