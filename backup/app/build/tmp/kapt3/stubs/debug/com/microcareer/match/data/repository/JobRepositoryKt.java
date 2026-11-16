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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\u0010\u0000\n\u0000\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0004\u001a\f\u0010\u0005\u001a\u00020\u0006*\u0004\u0018\u00010\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"TAG", "", "toEntity", "Lcom/microcareer/match/data/local/entity/JobEntity;", "Lcom/microcareer/match/data/remote/models/RemoteJob;", "toSafeLong", "", "", "app_debug"})
public final class JobRepositoryKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "JobRepository";
    
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.data.local.entity.JobEntity toEntity(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.models.RemoteJob $this$toEntity) {
        return null;
    }
    
    public static final long toSafeLong(@org.jetbrains.annotations.Nullable()
    java.lang.Object $this$toSafeLong) {
        return 0L;
    }
}