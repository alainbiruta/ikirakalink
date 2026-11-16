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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0006J\u0006\u0010\b\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/microcareer/match/data/sync/SyncManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "cancelSync", "", "schedulePeriodicSync", "triggerImmediateSync", "app_debug"})
public final class SyncManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    public SyncManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void schedulePeriodicSync() {
    }
    
    public final void triggerImmediateSync() {
    }
    
    public final void cancelSync() {
    }
}