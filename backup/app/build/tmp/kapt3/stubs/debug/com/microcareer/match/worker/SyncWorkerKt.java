package com.microcareer.match.worker;

import android.content.Context;
import android.util.Log;
import androidx.hilt.work.HiltWorker;
import androidx.work.CoroutineWorker;
import androidx.work.WorkerParameters;
import com.microcareer.match.data.local.OfflineActionQueue;
import com.microcareer.match.data.remote.models.ApplicationRequest;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.entity.OfflinePendingActionEntity;
import com.microcareer.match.data.remote.api.ApplicationsApiService;
import com.microcareer.match.data.local.dao.JobDao;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import com.microcareer.match.data.repository.UserRepository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0002"}, d2 = {"TAG", "", "app_debug"})
public final class SyncWorkerKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "SyncWorker";
}