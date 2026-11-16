package com.microcareer.match.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents an action taken while offline that must be synced when back online.
 *
 * Supported action types (ActionType constants below):
 *  APPLY_FOR_JOB  — user applied while offline
 *  SAVE_JOB       — user saved a job while offline
 *  UNSAVE_JOB     — user unsaved a job while offline
 *
 * Keep this table append-only. SyncWorker deletes rows after successful sync.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0087\b\u0018\u0000 !2\u00020\u0001:\u0001!B?\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\nH\u00c6\u0003JG\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\nH\u00d6\u0001J\t\u0010 \u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\""}, d2 = {"Lcom/microcareer/match/data/local/entity/OfflinePendingActionEntity;", "", "id", "", "actionType", "", "jobId", "payloadJson", "createdAt", "retryCount", "", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;JI)V", "getActionType", "()Ljava/lang/String;", "getCreatedAt", "()J", "getId", "getJobId", "getPayloadJson", "getRetryCount", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "Companion", "app_debug"})
@androidx.room.Entity(tableName = "offline_pending_actions")
public final class OfflinePendingActionEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    
    /**
     * One of the ActionType constants
     */
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String actionType = null;
    
    /**
     * The job ID this action targets
     */
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String jobId = null;
    
    /**
     * Optional payload serialised as JSON string.
     * For APPLY_FOR_JOB: {"cover_note": "..."} or null
     * For SAVE/UNSAVE: null
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String payloadJson = null;
    
    /**
     * Epoch millis when the action was queued
     */
    private final long createdAt = 0L;
    
    /**
     * How many times the sync worker has tried and failed.
     * After MAX_RETRIES the row is deleted to prevent infinite loops.
     */
    private final int retryCount = 0;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_APPLY_FOR_JOB = "APPLY_FOR_JOB";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_SAVE_JOB = "SAVE_JOB";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_UNSAVE_JOB = "UNSAVE_JOB";
    
    /**
     * Drop an action after this many failed sync attempts
     */
    public static final int MAX_RETRIES = 3;
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.data.local.entity.OfflinePendingActionEntity.Companion Companion = null;
    
    public OfflinePendingActionEntity(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String actionType, @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.Nullable()
    java.lang.String payloadJson, long createdAt, int retryCount) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    /**
     * One of the ActionType constants
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getActionType() {
        return null;
    }
    
    /**
     * The job ID this action targets
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getJobId() {
        return null;
    }
    
    /**
     * Optional payload serialised as JSON string.
     * For APPLY_FOR_JOB: {"cover_note": "..."} or null
     * For SAVE/UNSAVE: null
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPayloadJson() {
        return null;
    }
    
    /**
     * Epoch millis when the action was queued
     */
    public final long getCreatedAt() {
        return 0L;
    }
    
    /**
     * How many times the sync worker has tried and failed.
     * After MAX_RETRIES the row is deleted to prevent infinite loops.
     */
    public final int getRetryCount() {
        return 0;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    public final long component5() {
        return 0L;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.local.entity.OfflinePendingActionEntity copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String actionType, @org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.Nullable()
    java.lang.String payloadJson, long createdAt, int retryCount) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/microcareer/match/data/local/entity/OfflinePendingActionEntity$Companion;", "", "()V", "ACTION_APPLY_FOR_JOB", "", "ACTION_SAVE_JOB", "ACTION_UNSAVE_JOB", "MAX_RETRIES", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}