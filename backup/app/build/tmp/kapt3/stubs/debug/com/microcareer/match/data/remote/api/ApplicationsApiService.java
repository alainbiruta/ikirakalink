package com.microcareer.match.data.remote.api;

import com.microcareer.match.data.remote.models.*;
import retrofit2.Response;
import retrofit2.http.*;
import com.microcareer.match.data.remote.models.UpdateProfileRequest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J.\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ:\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00040\u00032\b\b\u0003\u0010\u000b\u001a\u00020\f2\b\b\u0003\u0010\r\u001a\u00020\f2\n\b\u0003\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0012J2\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00032\b\b\u0001\u0010\u0015\u001a\u00020\u00062\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u000e\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/microcareer/match/data/remote/api/ApplicationsApiService;", "", "createApplication", "Lretrofit2/Response;", "Lcom/microcareer/match/data/remote/models/ApiResponse;", "jobId", "", "request", "Lcom/microcareer/match/data/remote/models/ApplicationRequest;", "(Ljava/lang/String;Lcom/microcareer/match/data/remote/models/ApplicationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMyApplications", "page", "", "pageSize", "status", "(IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getReceivedApplications", "Lcom/microcareer/match/data/remote/models/ReceivedApplicationsResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateApplicationStatus", "", "applicantId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ApplicationsApiService {
    
    @retrofit2.http.POST(value = "api/v1/jobs/{jobId}/apply")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createApplication(@retrofit2.http.Path(value = "jobId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.models.ApplicationRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/applications/")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMyApplications(@retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "page_size")
    int pageSize, @retrofit2.http.Query(value = "status")
    @org.jetbrains.annotations.Nullable()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.lang.Object>>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/applications/received")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getReceivedApplications(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ReceivedApplicationsResponse>> $completion);
    
    @retrofit2.http.PATCH(value = "api/v1/applications/{applicantId}/status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateApplicationStatus(@retrofit2.http.Path(value = "applicantId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String applicantId, @retrofit2.http.Query(value = "job_id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @retrofit2.http.Query(value = "status")
    @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}