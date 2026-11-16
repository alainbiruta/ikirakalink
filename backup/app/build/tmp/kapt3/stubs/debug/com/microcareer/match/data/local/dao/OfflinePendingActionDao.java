package com.microcareer.match.data.local.dao;

import androidx.room.*;
import com.microcareer.match.data.local.entity.OfflinePendingActionEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\'\u00a8\u0006\u0014"}, d2 = {"Lcom/microcareer/match/data/local/dao/OfflinePendingActionDao;", "", "clearAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteExhausted", "getAll", "", "Lcom/microcareer/match/data/local/entity/OfflinePendingActionEntity;", "incrementRetry", "insert", "action", "(Lcom/microcareer/match/data/local/entity/OfflinePendingActionEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observePendingCount", "Lkotlinx/coroutines/flow/Flow;", "", "app_debug"})
@androidx.room.Dao()
public abstract interface OfflinePendingActionDao {
    
    /**
     * Insert a new pending action. Returns the new row id.
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.entity.OfflinePendingActionEntity action, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * All pending actions ordered by creation time (oldest first = FIFO)
     */
    @androidx.room.Query(value = "SELECT * FROM offline_pending_actions ORDER BY createdAt ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.microcareer.match.data.local.entity.OfflinePendingActionEntity>> $completion);
    
    /**
     * Reactive version — UI can show pending count if desired
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM offline_pending_actions")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> observePendingCount();
    
    /**
     * Delete a row after it synced successfully
     */
    @androidx.room.Query(value = "DELETE FROM offline_pending_actions WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Increment retry count after a transient failure
     */
    @androidx.room.Query(value = "\n        UPDATE offline_pending_actions\n        SET retryCount = retryCount + 1\n        WHERE id = :id\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object incrementRetry(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Remove rows that have hit the retry ceiling — called at start of each sync
     */
    @androidx.room.Query(value = "\n        DELETE FROM offline_pending_actions\n        WHERE retryCount >= 3\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteExhausted(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Wipe everything — used on logout
     */
    @androidx.room.Query(value = "DELETE FROM offline_pending_actions")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}