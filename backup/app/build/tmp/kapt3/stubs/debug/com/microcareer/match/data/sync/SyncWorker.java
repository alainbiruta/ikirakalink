package com.microcareer.match.data.sync;

import android.content.Context;
import androidx.hilt.work.HiltWorker;
import androidx.work.*;
import com.google.firebase.auth.FirebaseAuth;
import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.local.dao.LearningModuleDao;
import com.microcareer.match.data.local.dao.SkillProfileDao;
import com.microcareer.match.data.local.entity.JobEntity;
import com.microcareer.match.data.local.entity.LearningModuleEntity;
import com.microcareer.match.data.remote.api.MicroCareerApiService;
import com.microcareer.match.data.remote.dto.*;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import kotlinx.coroutines.Dispatchers;
import java.util.concurrent.TimeUnit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015BC\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\b\u0010\u0014\u001a\u00020\u0012H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/microcareer/match/data/sync/SyncWorker;", "Landroidx/work/CoroutineWorker;", "context", "Landroid/content/Context;", "params", "Landroidx/work/WorkerParameters;", "apiService", "Lcom/microcareer/match/data/remote/api/MicroCareerApiService;", "firebaseAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "jobDao", "Lcom/microcareer/match/data/local/dao/JobDao;", "learningModuleDao", "Lcom/microcareer/match/data/local/dao/LearningModuleDao;", "skillProfileDao", "Lcom/microcareer/match/data/local/dao/SkillProfileDao;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;Lcom/microcareer/match/data/remote/api/MicroCareerApiService;Lcom/google/firebase/auth/FirebaseAuth;Lcom/microcareer/match/data/local/dao/JobDao;Lcom/microcareer/match/data/local/dao/LearningModuleDao;Lcom/microcareer/match/data/local/dao/SkillProfileDao;)V", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "retryOrFail", "Companion", "app_debug"})
@androidx.hilt.work.HiltWorker()
public final class SyncWorker extends androidx.work.CoroutineWorker {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.remote.api.MicroCareerApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth firebaseAuth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.JobDao jobDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.LearningModuleDao learningModuleDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.SkillProfileDao skillProfileDao = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WORK_NAME = "sync_work";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_LAST_SYNC = "last_sync_timestamp";
    private static final int MAX_RETRY_COUNT = 3;
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.data.sync.SyncWorker.Companion Companion = null;
    
    @dagger.assisted.AssistedInject()
    public SyncWorker(@dagger.assisted.Assisted()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @dagger.assisted.Assisted()
    @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters params, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.api.MicroCareerApiService apiService, @org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth firebaseAuth, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.JobDao jobDao, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.LearningModuleDao learningModuleDao, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.SkillProfileDao skillProfileDao) {
        super(null, null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object doWork(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> $completion) {
        return null;
    }
    
    private final androidx.work.ListenableWorker.Result retryOrFail() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/microcareer/match/data/sync/SyncWorker$Companion;", "", "()V", "KEY_LAST_SYNC", "", "MAX_RETRY_COUNT", "", "WORK_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}