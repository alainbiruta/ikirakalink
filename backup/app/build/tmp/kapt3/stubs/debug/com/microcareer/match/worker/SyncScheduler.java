package com.microcareer.match.worker;

import android.content.Context;
import android.util.Log;
import androidx.work.*;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Schedules [SyncWorker] to run once when the device has network access.
 *
 * Call [scheduleSyncOnConnectivity] any time you enqueue an offline action.
 * WorkManager deduplicates — calling it 10 times only schedules 1 job.
 *
 * Call [cancelSync] on logout.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/microcareer/match/worker/SyncScheduler;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "workManager", "Landroidx/work/WorkManager;", "cancelSync", "", "scheduleSyncOnConnectivity", "app_debug"})
public final class SyncScheduler {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.work.WorkManager workManager = null;
    
    @javax.inject.Inject()
    public SyncScheduler(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Enqueue a one-shot sync that waits for network connectivity.
     * Safe to call multiple times — uses KEEP policy so an already-queued
     * or running sync is never replaced.
     */
    public final void scheduleSyncOnConnectivity() {
    }
    
    /**
     * Cancel any pending sync work — call on logout.
     */
    public final void cancelSync() {
    }
}