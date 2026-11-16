package com.microcareer.match.data.remote.api;

import com.microcareer.match.data.remote.models.*;
import retrofit2.Response;
import retrofit2.http.*;
import com.microcareer.match.data.remote.models.UpdateProfileRequest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J(\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0003\u0010\n\u001a\u00020\u000b2\b\b\u0003\u0010\f\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\rJ$\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007JX\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\b\b\u0003\u0010\u0013\u001a\u00020\u000b2\b\b\u0003\u0010\u0014\u001a\u00020\u000b2\n\b\u0003\u0010\u0015\u001a\u0004\u0018\u00010\u00062\n\b\u0003\u0010\u0016\u001a\u0004\u0018\u00010\u00062\n\b\u0003\u0010\u0017\u001a\u0004\u0018\u00010\u00182\n\b\u0003\u0010\u0019\u001a\u0004\u0018\u00010\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ:\u0010\u001b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u001c0\u000f0\u00032\b\b\u0003\u0010\u0014\u001a\u00020\u000b2\b\b\u0003\u0010\u001d\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ$\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u00032\b\b\u0001\u0010 \u001a\u00020!H\u00a7@\u00a2\u0006\u0002\u0010\"JJ\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\b\b\u0001\u0010$\u001a\u00020\u00062\n\b\u0003\u0010\u0015\u001a\u0004\u0018\u00010\u00062\n\b\u0003\u0010\u0016\u001a\u0004\u0018\u00010\u00062\b\b\u0003\u0010\u0013\u001a\u00020\u000b2\b\b\u0003\u0010\u0014\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010%J(\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010 \u001a\u00020!H\u00a7@\u00a2\u0006\u0002\u0010\'J4\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0014\b\u0001\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u001cH\u00a7@\u00a2\u0006\u0002\u0010*\u00a8\u0006+"}, d2 = {"Lcom/microcareer/match/data/remote/api/JobsApiService;", "", "deleteJob", "Lretrofit2/Response;", "", "jobId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEmployerJobs", "Lcom/microcareer/match/data/remote/models/JobListApiResponse;", "page", "", "pageSize", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getJob", "Lcom/microcareer/match/data/remote/models/ApiResponse;", "Lcom/microcareer/match/data/remote/models/RemoteJob;", "getJobs", "Lcom/microcareer/match/data/remote/models/JobsResponse;", "skip", "limit", "category", "location", "minPayment", "", "maxPayment", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecommendations", "", "minScore", "(IDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postJob", "request", "Lcom/microcareer/match/data/remote/models/PostJobRequest;", "(Lcom/microcareer/match/data/remote/models/PostJobRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchJobs", "query", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateJob", "(Ljava/lang/String;Lcom/microcareer/match/data/remote/models/PostJobRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateJobStatus", "body", "(Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface JobsApiService {
    
    @retrofit2.http.GET(value = "api/v1/jobs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJobs(@retrofit2.http.Query(value = "skip")
    int skip, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "category")
    @org.jetbrains.annotations.Nullable()
    java.lang.String category, @retrofit2.http.Query(value = "location")
    @org.jetbrains.annotations.Nullable()
    java.lang.String location, @retrofit2.http.Query(value = "min_payment")
    @org.jetbrains.annotations.Nullable()
    java.lang.Double minPayment, @retrofit2.http.Query(value = "max_payment")
    @org.jetbrains.annotations.Nullable()
    java.lang.Double maxPayment, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.JobsResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/jobs/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJob(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<com.microcareer.match.data.remote.models.RemoteJob>>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/matching/recommendations/advanced")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRecommendations(@retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "min_score")
    double minScore, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<java.util.Map<java.lang.String, java.lang.Object>>>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/jobs/search")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchJobs(@retrofit2.http.Query(value = "q")
    @org.jetbrains.annotations.NotNull()
    java.lang.String query, @retrofit2.http.Query(value = "category")
    @org.jetbrains.annotations.Nullable()
    java.lang.String category, @retrofit2.http.Query(value = "location")
    @org.jetbrains.annotations.Nullable()
    java.lang.String location, @retrofit2.http.Query(value = "skip")
    int skip, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.JobsResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/employer/jobs/mine")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEmployerJobs(@retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "page_size")
    int pageSize, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.JobListApiResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/employer/jobs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object postJob(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.models.PostJobRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.ApiResponse<com.microcareer.match.data.remote.models.RemoteJob>>> $completion);
    
    @retrofit2.http.PUT(value = "api/v1/employer/jobs/{jobId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateJob(@retrofit2.http.Path(value = "jobId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.models.PostJobRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.RemoteJob>> $completion);
    
    @retrofit2.http.DELETE(value = "api/v1/employer/jobs/{jobId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteJob(@retrofit2.http.Path(value = "jobId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.PATCH(value = "api/v1/employer/jobs/{jobId}/status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateJobStatus(@retrofit2.http.Path(value = "jobId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.microcareer.match.data.remote.models.RemoteJob>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}