package com.microcareer.match.data.remote.api;

import com.microcareer.match.data.remote.models.*;
import retrofit2.Response;
import retrofit2.http.*;
import com.microcareer.match.data.remote.models.UpdateProfileRequest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\b\b\u0001\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\b\b\u0001\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\b\b\u0001\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J$\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00160\u00032\b\b\u0001\u0010\u0012\u001a\u00020\u0017H\u00a7@\u00a2\u0006\u0002\u0010\u0018J$\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00160\u00032\b\b\u0001\u0010\u0012\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010\u001b\u00a8\u0006\u001c"}, d2 = {"Lcom/microcareer/match/data/remote/api/AuthApiService;", "", "getCurrentUser", "Lretrofit2/Response;", "Lcom/microcareer/match/data/remote/models/RemoteUser;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/microcareer/match/data/remote/models/AuthResponse;", "username", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "request", "Lcom/microcareer/match/data/remote/models/RegisterRequest;", "(Lcom/microcareer/match/data/remote/models/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateFcmToken", "", "body", "Lcom/microcareer/match/data/remote/models/FcmTokenRequest;", "(Lcom/microcareer/match/data/remote/models/FcmTokenRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProfile", "Lcom/microcareer/match/data/remote/models/ApiResponse;", "Lcom/microcareer/match/data/remote/models/UpdateProfileRequest;", "(Lcom/microcareer/match/data/remote/models/UpdateProfileRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSkills", "Lcom/microcareer/match/data/remote/models/SkillsUpdateRequest;", "(Lcom/microcareer/match/data/remote/models/SkillsUpdateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface AuthApiService {
    
    @retrofit2.http.POST(value = "api/v1/auth/token")
    @retrofit2.http.FormUrlEncoded()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Field(value = "username")
    @org.jetbrains.annotations.NotNull()
    java.lang.String username, @retrofit2.http.Field(value = "password")
    @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.AuthResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/auth/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object register(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.models.RegisterRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.AuthResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/users/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCurrentUser(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.RemoteUser>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/auth/fcm-token")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateFcmToken(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.models.FcmTokenRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.PUT(value = "api/v1/users/me/skills")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSkills(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.models.SkillsUpdateRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
    
    @retrofit2.http.PUT(value = "api/v1/users/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateProfile(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.models.UpdateProfileRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
}