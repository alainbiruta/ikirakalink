package com.microcareer.match.data.local;

import android.util.Log;
import com.microcareer.match.data.local.dao.OfflinePendingActionDao;
import com.microcareer.match.data.local.entity.OfflinePendingActionEntity;
import kotlinx.coroutines.flow.Flow;
import org.json.JSONObject;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Wraps [OfflinePendingActionDao] and provides typed enqueue helpers.
 * Inject this into [JobRepository] — do not inject the DAO directly.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0007J \u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0086@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0012\u0010\u0010\u001a\u0004\u0018\u00010\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\nJ\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0018J\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bJ\u000e\u0010\u001d\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/microcareer/match/data/local/OfflineActionQueue;", "", "dao", "Lcom/microcareer/match/data/local/dao/OfflinePendingActionDao;", "(Lcom/microcareer/match/data/local/dao/OfflinePendingActionDao;)V", "clearAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "enqueueApply", "jobId", "", "coverNote", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "enqueueSave", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "enqueueUnsave", "extractCoverNote", "payloadJson", "getAll", "", "Lcom/microcareer/match/data/local/entity/OfflinePendingActionEntity;", "markFailed", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markSynced", "observePendingCount", "Lkotlinx/coroutines/flow/Flow;", "", "pruneExhausted", "app_debug"})
public final class OfflineActionQueue {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.OfflinePendingActionDao dao = null;
    
    @javax.inject.Inject()
    public OfflineActionQueue(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.OfflinePendingActionDao dao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object enqueueApply(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.Nullable()
    java.lang.String coverNote, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object enqueueSave(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object enqueueUnsave(@org.jetbrains.annotations.NotNull()
    java.lang.String jobId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.microcareer.match.data.local.entity.OfflinePendingActionEntity>> $completion) {
        return null;
    }
    
    /**
     * Reactive count — wire to UI badge if desired
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> observePendingCount() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markSynced(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markFailed(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object pruneExhausted(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Call on logout
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String extractCoverNote(@org.jetbrains.annotations.Nullable()
    java.lang.String payloadJson) {
        return null;
    }
}