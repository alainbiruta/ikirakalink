package com.microcareer.match.data.local.dao;

import androidx.room.*;
import com.microcareer.match.data.local.entity.JobEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fH\'J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fH\'J\u0018\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000f2\u0006\u0010\u0007\u001a\u00020\bH\'J\u0018\u0010\u0015\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000f2\u0006\u0010\u0017\u001a\u00020\bH\'J\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0019\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fH\'J\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001c\u0010\u001f\u001a\u00020\u00032\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00a7@\u00a2\u0006\u0002\u0010!J\u001c\u0010\"\u001a\u00020\u00032\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0097@\u00a2\u0006\u0002\u0010!J\u001c\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000f2\u0006\u0010$\u001a\u00020\bH\'J(\u0010%\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010&\u001a\u00020\'2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010(J\u0016\u0010)\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001e\u0010*\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010+\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010,J\u001e\u0010-\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010.\u001a\u00020/H\u00a7@\u00a2\u0006\u0002\u00100J(\u00101\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00102\u001a\u00020\'2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010(J\u001e\u00103\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00104\u001a\u00020\'H\u00a7@\u00a2\u0006\u0002\u00105\u00a8\u00066"}, d2 = {"Lcom/microcareer/match/data/local/dao/JobDao;", "", "clearAllJobs", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllJobs", "deleteJob", "jobId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldJobs", "timestamp", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllAvailableJobs", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/microcareer/match/data/local/entity/JobEntity;", "getAllJobs", "getAppliedJobs", "getJob", "getJobOnce", "getJobsByCategory", "category", "getJobsByEmployer", "employerId", "getSavedJobs", "getUnsyncedJobs", "insertJob", "job", "(Lcom/microcareer/match/data/local/entity/JobEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertJobs", "jobs", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "replaceAllJobs", "searchJobs", "query", "updateAppliedStatus", "applied", "", "(Ljava/lang/String;ZJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateJob", "updateJobStatus", "status", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMatchScore", "score", "", "(Ljava/lang/String;FLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSavedStatus", "saved", "updateSyncStatus", "synced", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface JobDao {
    
    @androidx.room.Query(value = "SELECT * FROM jobs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllJobs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.microcareer.match.data.local.entity.JobEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM jobs WHERE status = \'OPEN\' ORDER BY matchScore DESC, createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.JobEntity>> getAllAvailableJobs();
    
    @androidx.room.Query(value = "SELECT * FROM jobs WHERE id = :jobId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.microcareer.match.data.local.entity.JobEntity> getJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId);
    
    @androidx.room.Query(value = "SELECT * FROM jobs WHERE id = :jobId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJobOnce(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.microcareer.match.data.local.entity.JobEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM jobs WHERE isSaved = 1 ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.JobEntity>> getSavedJobs();
    
    @androidx.room.Query(value = "SELECT * FROM jobs WHERE isApplied = 1 ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.JobEntity>> getAppliedJobs();
    
    @androidx.room.Query(value = "SELECT * FROM jobs WHERE category = :category AND status = \'OPEN\' ORDER BY matchScore DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.JobEntity>> getJobsByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category);
    
    /**
     * Full-text search across title, employerName, category, and location.
     * LIKE is case-insensitive for ASCII in SQLite by default.
     * Results ordered by matchScore so best fits appear first.
     */
    @androidx.room.Query(value = "\n        SELECT * FROM jobs\n        WHERE status = \'OPEN\'\n          AND (\n               title        LIKE \'%\' || :query || \'%\'\n            OR employerName LIKE \'%\' || :query || \'%\'\n            OR category     LIKE \'%\' || :query || \'%\'\n            OR location     LIKE \'%\' || :query || \'%\'\n          )\n        ORDER BY matchScore DESC, createdAt DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.JobEntity>> searchJobs(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertJob(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.entity.JobEntity job, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertJobs(@org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> jobs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateJob(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.entity.JobEntity job, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE jobs SET isSaved = :saved, updatedAt = :timestamp WHERE id = :jobId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSavedStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, boolean saved, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE jobs SET isApplied = :applied, updatedAt = :timestamp WHERE id = :jobId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateAppliedStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, boolean applied, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE jobs SET isSynced = :synced WHERE id = :jobId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSyncStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, boolean synced, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM jobs WHERE isSynced = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedJobs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.microcareer.match.data.local.entity.JobEntity>> $completion);
    
    @androidx.room.Query(value = "DELETE FROM jobs WHERE id = :jobId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteJob(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM jobs WHERE status != \'APPLIED\' AND updatedAt < :timestamp")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteOldJobs(long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM jobs WHERE employerId = :employerId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJobsByEmployer(@org.jetbrains.annotations.NotNull()
    java.lang.String employerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.microcareer.match.data.local.entity.JobEntity>> $completion);
    
    @androidx.room.Query(value = "UPDATE jobs SET status = :status WHERE id = :jobId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateJobStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM jobs WHERE isApplied = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllJobs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Transaction()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object replaceAllJobs(@org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.JobEntity> jobs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM jobs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearAllJobs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE jobs SET matchScore = :score WHERE id = :jobId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateMatchScore(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, float score, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        
        @androidx.room.Transaction()
        @org.jetbrains.annotations.Nullable()
        public static java.lang.Object replaceAllJobs(@org.jetbrains.annotations.NotNull()
        com.microcareer.match.data.local.dao.JobDao $this, @org.jetbrains.annotations.NotNull()
        java.util.List<com.microcareer.match.data.local.entity.JobEntity> jobs, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}