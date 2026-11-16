package com.microcareer.match.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG          = "SyncScheduler"
private const val WORK_TAG     = "offline_sync"

/**
 * Schedules [SyncWorker] to run once when the device has network access.
 *
 * Call [scheduleSyncOnConnectivity] any time you enqueue an offline action.
 * WorkManager deduplicates — calling it 10 times only schedules 1 job.
 *
 * Call [cancelSync] on logout.
 */
@Singleton
class SyncScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val workManager = WorkManager.getInstance(context)

    /**
     * Enqueue a one-shot sync that waits for network connectivity.
     * Safe to call multiple times — uses KEEP policy so an already-queued
     * or running sync is never replaced.
     */
    fun scheduleSyncOnConnectivity() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .addTag(WORK_TAG)
            // Exponential back-off: 10s, 20s, 40s … capped at 5min
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                androidx.work.WorkRequest.MIN_BACKOFF_MILLIS,
                java.util.concurrent.TimeUnit.MILLISECONDS
            )
            .build()

        workManager.enqueueUniqueWork(
            WORK_TAG,
            ExistingWorkPolicy.KEEP,   // Don't interrupt a running sync
            request
        )

        Log.d(TAG, "Sync scheduled (KEEP policy — won't duplicate)")
    }

    /**
     * Cancel any pending sync work — call on logout.
     */
    fun cancelSync() {
        workManager.cancelAllWorkByTag(WORK_TAG)
        Log.d(TAG, "Sync cancelled")
    }
}