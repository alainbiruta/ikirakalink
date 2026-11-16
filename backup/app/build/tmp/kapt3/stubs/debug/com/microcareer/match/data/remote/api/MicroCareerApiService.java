package com.microcareer.match.data.remote.api;

import com.microcareer.match.data.remote.dto.*;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J8\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010\b\u001a\u00020\u00072\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ$\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ.\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010\b\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0011Jn\u0010\u0012\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00130\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0003\u0010\u0014\u001a\u00020\u00152\b\b\u0003\u0010\u0016\u001a\u00020\u00152\n\b\u0003\u0010\u0017\u001a\u0004\u0018\u00010\u00072\n\b\u0003\u0010\u0018\u001a\u0004\u0018\u00010\u00192\n\b\u0003\u0010\u001a\u001a\u0004\u0018\u00010\u00192\n\b\u0003\u0010\u001b\u001a\u0004\u0018\u00010\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u001cJ*\u0010\u001d\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ*\u0010 \u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u001e0\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ*\u0010!\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ.\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010$\u001a\u00020%H\u00a7@\u00a2\u0006\u0002\u0010&J.\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010(\u001a\u00020\u00072\b\b\u0001\u0010)\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0011J.\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010\b\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0011J.\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010-\u001a\u00020.H\u00a7@\u00a2\u0006\u0002\u0010/J.\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010\b\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0011J.\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u00102\u001a\u000203H\u00a7@\u00a2\u0006\u0002\u00104J.\u00105\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u00106\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u00107J.\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u00109\u001a\u00020:H\u00a7@\u00a2\u0006\u0002\u0010;J.\u0010<\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010=\u001a\u00020:H\u00a7@\u00a2\u0006\u0002\u0010;J4\u0010>\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\u000e\b\u0001\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00070\u001eH\u00a7@\u00a2\u0006\u0002\u0010@\u00a8\u0006A"}, d2 = {"Lcom/microcareer/match/data/remote/api/MicroCareerApiService;", "", "applyForJob", "Lretrofit2/Response;", "Lcom/microcareer/match/data/remote/dto/ApiResponse;", "", "token", "", "jobId", "application", "Lcom/microcareer/match/data/remote/dto/JobApplication;", "(Ljava/lang/String;Ljava/lang/String;Lcom/microcareer/match/data/remote/dto/JobApplication;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentUser", "Lcom/microcareer/match/data/remote/dto/UserDto;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getJob", "Lcom/microcareer/match/data/remote/dto/JobDto;", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getJobs", "Lcom/microcareer/match/data/remote/dto/PaginatedResponse;", "page", "", "pageSize", "category", "latitude", "", "longitude", "radius", "(Ljava/lang/String;IILjava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLearningModules", "", "Lcom/microcareer/match/data/remote/dto/LearningModuleDto;", "getRecommendedJobs", "getRecommendedLearning", "inferSkills", "Lcom/microcareer/match/data/remote/dto/SkillInferenceResponse;", "request", "Lcom/microcareer/match/data/remote/dto/SkillInputRequest;", "(Ljava/lang/String;Lcom/microcareer/match/data/remote/dto/SkillInputRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerFcmToken", "Token", "fcmToken", "saveJob", "syncData", "Lcom/microcareer/match/data/remote/dto/SyncResponse;", "syncRequest", "Lcom/microcareer/match/data/remote/dto/SyncRequest;", "(Ljava/lang/String;Lcom/microcareer/match/data/remote/dto/SyncRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unsaveJob", "updateLearningProgress", "progress", "Lcom/microcareer/match/data/remote/dto/LearningProgress;", "(Ljava/lang/String;Lcom/microcareer/match/data/remote/dto/LearningProgress;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUser", "user", "(Ljava/lang/String;Lcom/microcareer/match/data/remote/dto/UserDto;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadProfilePicture", "image", "Lokhttp3/MultipartBody$Part;", "(Ljava/lang/String;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadVoiceNote", "audio", "verifySkill", "skills", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface MicroCareerApiService {
    
    @retrofit2.http.GET(value = "users/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCurrentUser(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<com.microcareer.match.data.remote.dto.UserDto>>> $completion);
    
    @retrofit2.http.PUT(value = "users/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateUser(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.dto.UserDto user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<com.microcareer.match.data.remote.dto.UserDto>>> $completion);
    
    @retrofit2.http.Multipart()
    @retrofit2.http.POST(value = "users/me/profile-picture")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object uploadProfilePicture(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Part()
    @org.jetbrains.annotations.NotNull()
    okhttp3.MultipartBody.Part image, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<java.lang.String>>> $completion);
    
    @retrofit2.http.GET(value = "jobs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJobs(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "pageSize")
    int pageSize, @retrofit2.http.Query(value = "category")
    @org.jetbrains.annotations.Nullable()
    java.lang.String category, @retrofit2.http.Query(value = "lat")
    @org.jetbrains.annotations.Nullable()
    java.lang.Double latitude, @retrofit2.http.Query(value = "lng")
    @org.jetbrains.annotations.Nullable()
    java.lang.Double longitude, @retrofit2.http.Query(value = "radius")
    @org.jetbrains.annotations.Nullable()
    java.lang.Integer radius, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<com.microcareer.match.data.remote.dto.PaginatedResponse<com.microcareer.match.data.remote.dto.JobDto>>>> $completion);
    
    @retrofit2.http.GET(value = "jobs/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJob(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<com.microcareer.match.data.remote.dto.JobDto>>> $completion);
    
    @retrofit2.http.GET(value = "jobs/recommendations")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRecommendedJobs(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<java.util.List<com.microcareer.match.data.remote.dto.JobDto>>>> $completion);
    
    @retrofit2.http.POST(value = "jobs/{id}/apply")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object applyForJob(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.dto.JobApplication application, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<kotlin.Unit>>> $completion);
    
    @retrofit2.http.POST(value = "jobs/{id}/save")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveJob(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<kotlin.Unit>>> $completion);
    
    @retrofit2.http.DELETE(value = "jobs/{id}/save")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object unsaveJob(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<kotlin.Unit>>> $completion);
    
    @retrofit2.http.POST(value = "extract-skills")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object inferSkills(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.dto.SkillInputRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<com.microcareer.match.data.remote.dto.SkillInferenceResponse>>> $completion);
    
    @retrofit2.http.Multipart()
    @retrofit2.http.POST(value = "skills/voice-upload")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object uploadVoiceNote(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Part()
    @org.jetbrains.annotations.NotNull()
    okhttp3.MultipartBody.Part audio, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<com.microcareer.match.data.remote.dto.SkillInferenceResponse>>> $completion);
    
    @retrofit2.http.POST(value = "add-skills")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object verifySkill(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<kotlin.Unit>>> $completion);
    
    @retrofit2.http.GET(value = "learning/modules")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLearningModules(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<java.util.List<com.microcareer.match.data.remote.dto.LearningModuleDto>>>> $completion);
    
    @retrofit2.http.GET(value = "learning/recommendations")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRecommendedLearning(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<java.util.List<com.microcareer.match.data.remote.dto.LearningModuleDto>>>> $completion);
    
    @retrofit2.http.POST(value = "learning/progress")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLearningProgress(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.dto.LearningProgress progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<kotlin.Unit>>> $completion);
    
    @retrofit2.http.POST(value = "sync")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object syncData(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.dto.SyncRequest syncRequest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<com.microcareer.match.data.remote.dto.SyncResponse>>> $completion);
    
    @retrofit2.http.POST(value = "notifications/token")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object registerFcmToken(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String Token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.lang.String fcmToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.dto.ApiResponse<kotlin.Unit>>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}