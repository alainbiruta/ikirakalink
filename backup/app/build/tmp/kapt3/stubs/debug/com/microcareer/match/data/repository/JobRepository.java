package com.microcareer.match.data.repository;

import android.util.Log;
import com.microcareer.match.data.local.OfflineActionQueue;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.local.entity.JobEntity;
import com.microcareer.match.data.remote.api.ApplicationsApiService;
import com.microcareer.match.data.remote.api.JobsApiService;
import com.microcareer.match.data.remote.models.PostJobRequest;
import com.microcareer.match.data.remote.models.RemoteJob;
import com.microcareer.match.data.remote.models.RemoteApplication;
import com.microcareer.match.presentation.employer.ApplicantUiModel;
import com.microcareer.match.data.remote.models.ApplicationRequest;
import com.microcareer.match.utils.NetworkMonitor;
import com.microcareer.match.worker.SyncScheduler;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlinx.coroutines.flow.SharedFlow;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0006\n\u0002\b\u0016\b\u0007\u0018\u00002\u00020\u0001B?\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0010J&\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001a0\u00192\u0006\u0010\u001c\u001a\u00020\u001d2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001dJ\u0016\u0010\u001f\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010 J\"\u0010!\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00010#\u0018\u00010\"H\u0086@\u00a2\u0006\u0002\u0010$J\u001a\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0\"0\u001aH\u0086@\u00a2\u0006\u0002\u0010$J\u0012\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\"0\u0019J\u0018\u0010)\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\"0\u001a0\u0019J\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u001a0\u00192\u0006\u0010\u001c\u001a\u00020\u001dJD\u0010+\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\"0\u001a0\u00192\b\b\u0002\u0010,\u001a\u00020-2\b\b\u0002\u0010.\u001a\u00020-2\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u001d2\n\b\u0002\u00100\u001a\u0004\u0018\u00010\u001dJ\u001a\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\"0\u00192\u0006\u0010/\u001a\u00020\u001dJ\u0018\u00102\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\"0\u001a0\u0019J\u0012\u00103\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\"0\u0019J\f\u00104\u001a\b\u0012\u0004\u0012\u00020-0\u0019J~\u00105\u001a\u00020\u00132\u0006\u00106\u001a\u00020\u001d2\u0006\u00107\u001a\u00020\u001d2\u0006\u00100\u001a\u00020\u001d2\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u001d2\u0006\u0010;\u001a\u00020\u001d2\u0006\u0010/\u001a\u00020\u001d2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001d0\"2\b\b\u0002\u0010=\u001a\u00020\u001b2\b\b\u0002\u0010>\u001a\u00020-2\b\b\u0002\u0010?\u001a\u00020-2\n\b\u0002\u0010@\u001a\u0004\u0018\u00010\u001dH\u0086@\u00a2\u0006\u0002\u0010AJ\u0016\u0010B\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010 J\u001a\u0010C\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\"0\u00192\u0006\u0010D\u001a\u00020\u001dJ\u000e\u0010E\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010$J\u0016\u0010F\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010 J&\u0010G\u001a\u00020\u00132\u0006\u0010H\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010I\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010JJ\u0086\u0001\u0010K\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u00106\u001a\u00020\u001d2\u0006\u00107\u001a\u00020\u001d2\u0006\u00100\u001a\u00020\u001d2\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u001d2\u0006\u0010;\u001a\u00020\u001d2\u0006\u0010/\u001a\u00020\u001d2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001d0\"2\b\b\u0002\u0010=\u001a\u00020\u001b2\b\b\u0002\u0010>\u001a\u00020-2\b\b\u0002\u0010?\u001a\u00020-2\n\b\u0002\u0010@\u001a\u0004\u0018\u00010\u001dH\u0086@\u00a2\u0006\u0002\u0010LJ\u001e\u0010M\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010I\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010NR\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006O"}, d2 = {"Lcom/microcareer/match/data/repository/JobRepository;", "", "jobsApiService", "Lcom/microcareer/match/data/remote/api/JobsApiService;", "applicationsApiService", "Lcom/microcareer/match/data/remote/api/ApplicationsApiService;", "jobDao", "Lcom/microcareer/match/data/local/dao/JobDao;", "offlineActionQueue", "Lcom/microcareer/match/data/local/OfflineActionQueue;", "networkMonitor", "Lcom/microcareer/match/utils/NetworkMonitor;", "syncScheduler", "Lcom/microcareer/match/worker/SyncScheduler;", "tokenManager", "Lcom/microcareer/match/data/local/TokenManager;", "(Lcom/microcareer/match/data/remote/api/JobsApiService;Lcom/microcareer/match/data/remote/api/ApplicationsApiService;Lcom/microcareer/match/data/local/dao/JobDao;Lcom/microcareer/match/data/local/OfflineActionQueue;Lcom/microcareer/match/utils/NetworkMonitor;Lcom/microcareer/match/worker/SyncScheduler;Lcom/microcareer/match/data/local/TokenManager;)V", "_jobsChanged", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "jobsChanged", "Lkotlinx/coroutines/flow/SharedFlow;", "getJobsChanged", "()Lkotlinx/coroutines/flow/SharedFlow;", "applyForJob", "Lkotlinx/coroutines/flow/Flow;", "Lcom/microcareer/match/data/repository/AppResult;", "", "jobId", "", "coverNote", "deleteJob", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchMyApplicationsRaw", "", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getApplicants", "Lcom/microcareer/match/data/remote/models/RemoteApplication;", "getAppliedJobs", "Lcom/microcareer/match/data/local/entity/JobEntity;", "getEmployerJobs", "getJobById", "getJobs", "skip", "", "limit", "category", "location", "getJobsByCategory", "getRecommendations", "getSavedJobs", "observePendingSyncCount", "postJob", "title", "description", "payment", "", "currency", "duration", "skills", "isUrgent", "requiredHours", "maxApplicants", "deadline", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZIILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveJob", "searchJobs", "query", "syncAppliedJobsFromServer", "unsaveJob", "updateApplicationStatus", "applicantId", "status", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateJob", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZIILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateJobStatus", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class JobRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.remote.api.JobsApiService jobsApiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.remote.api.ApplicationsApiService applicationsApiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.JobDao jobDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.OfflineActionQueue offlineActionQueue = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.utils.NetworkMonitor networkMonitor = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.worker.SyncScheduler syncScheduler = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.TokenManager tokenManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Unit> _jobsChanged = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> jobsChanged = null;
    
    @javax.inject.Inject()
    public JobRepository(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.api.JobsApiService jobsApiService, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.api.ApplicationsApiService applicationsApiService, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.JobDao jobDao, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.OfflineActionQueue offlineActionQueue, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.utils.NetworkMonitor networkMonitor, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.worker.SyncScheduler syncScheduler, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.TokenManager tokenManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> getJobsChanged() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.microcareer.match.data.repository.AppResult<java.util.List<com.microcareer.match.data.local.entity.JobEntity>>> getJobs(int skip, int limit, @org.jetbrains.annotations.Nullable()
    java.lang.String category, @org.jetbrains.annotations.Nullable()
    java.lang.String location) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.microcareer.match.data.repository.AppResult<com.microcareer.match.data.local.entity.JobEntity>> getJobById(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.microcareer.match.data.repository.AppResult<java.util.List<com.microcareer.match.data.local.entity.JobEntity>>> getRecommendations() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object fetchMyApplicationsRaw(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<? extends java.util.Map<java.lang.String, ? extends java.lang.Object>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object unsaveJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.JobEntity>> getSavedJobs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.microcareer.match.data.repository.AppResult<java.lang.Boolean>> applyForJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.Nullable()
    java.lang.String coverNote) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.JobEntity>> searchJobs(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.JobEntity>> getJobsByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.JobEntity>> getAppliedJobs() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncAppliedJobsFromServer(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> observePendingSyncCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.microcareer.match.data.repository.AppResult<java.util.List<com.microcareer.match.data.local.entity.JobEntity>>> getEmployerJobs() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getApplicants(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.microcareer.match.data.repository.AppResult<? extends java.util.List<com.microcareer.match.data.remote.models.RemoteApplication>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object postJob(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String location, double payment, @org.jetbrains.annotations.NotNull()
    java.lang.String currency, @org.jetbrains.annotations.NotNull()
    java.lang.String duration, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, boolean isUrgent, int requiredHours, int maxApplicants, @org.jetbrains.annotations.Nullable()
    java.lang.String deadline, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String location, double payment, @org.jetbrains.annotations.NotNull()
    java.lang.String currency, @org.jetbrains.annotations.NotNull()
    java.lang.String duration, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, boolean isUrgent, int requiredHours, int maxApplicants, @org.jetbrains.annotations.Nullable()
    java.lang.String deadline, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateJobStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateApplicationStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String applicantId, @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}