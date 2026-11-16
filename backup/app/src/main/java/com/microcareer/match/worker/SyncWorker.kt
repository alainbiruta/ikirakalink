package com.microcareer.match.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.microcareer.match.data.local.OfflineActionQueue
import com.microcareer.match.data.remote.models.ApplicationRequest
import com.microcareer.match.data.local.TokenManager
import com.microcareer.match.data.local.entity.OfflinePendingActionEntity
import com.microcareer.match.data.remote.api.ApplicationsApiService
import com.microcareer.match.data.local.dao.JobDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import com.microcareer.match.data.repository.UserRepository

private const val TAG = "SyncWorker"

/**
 * Drains [OfflineActionQueue] row-by-row when the device is back online.
 *
 * Scheduled by [SyncScheduler.scheduleSyncOnConnectivity] — runs once per
 * connectivity restore (not periodic). WorkManager handles retries for the
 * worker itself; per-action retries are tracked in [OfflinePendingActionEntity.retryCount].
 *
 * Return values:
 *   Result.success() — all rows processed (sync'd or permanently failed)
 *   Result.retry()   — transient error (WorkManager will reschedule)
 *   Result.failure() — unrecoverable error (should not happen)
 */
@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val offlineActionQueue: OfflineActionQueue,
    private val applicationsApiService: ApplicationsApiService,
    private val jobDao: JobDao,
    private val tokenManager: TokenManager,
    private val userRepository: UserRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d(TAG, "SyncWorker started")

        // Guard: no token = user logged out while offline, nothing to sync
        if (tokenManager.getAccessToken() == null) {
            Log.d(TAG, "No auth token — clearing queue and exiting")
            offlineActionQueue.clearAll()
            return Result.success()
        }

        // Remove any rows that hit the retry ceiling before we start
        offlineActionQueue.pruneExhausted()
        //Sync any skill updates that were saved offline
        userRepository.syncUnsyncedUsers()
        val pending = offlineActionQueue.getAll()
        if (pending.isEmpty()) {
            Log.d(TAG, "Nothing to sync")
            return Result.success()
        }

        Log.d(TAG, "Syncing ${pending.size} pending action(s)")

        var hasTransientFailure = false

        for (action in pending) {
            val success = processAction(action)
            when {
                success -> {
                    offlineActionQueue.markSynced(action.id)
                    Log.d(TAG, "✅ Synced ${action.actionType} jobId=${action.jobId}")
                }
                action.retryCount + 1 >= OfflinePendingActionEntity.MAX_RETRIES -> {
                    // This was the last allowed retry — mark failed so pruneExhausted
                    // removes it on the next run
                    offlineActionQueue.markFailed(action.id)
                    Log.w(TAG, "❌ Giving up on ${action.actionType} jobId=${action.jobId} " +
                            "after ${action.retryCount + 1} attempts")
                }
                else -> {
                    offlineActionQueue.markFailed(action.id)
                    hasTransientFailure = true
                    Log.w(TAG, "⚠️ Transient failure for ${action.actionType} — will retry")
                }
            }
        }

        return if (hasTransientFailure) Result.retry() else Result.success()
    }

    // ─────────────────────────────────────────────────────
    // Dispatch by action type
    // ─────────────────────────────────────────────────────

    private suspend fun processAction(action: OfflinePendingActionEntity): Boolean {
        return try {
            when (action.actionType) {
                OfflinePendingActionEntity.ACTION_APPLY_FOR_JOB -> syncApply(action)
                OfflinePendingActionEntity.ACTION_SAVE_JOB      -> syncSave(action.jobId, saved = true)
                OfflinePendingActionEntity.ACTION_UNSAVE_JOB    -> syncSave(action.jobId, saved = false)
                else -> {
                    Log.e(TAG, "Unknown action type: ${action.actionType} — skipping")
                    true // Treat unknown as handled so it doesn't loop forever
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception processing action ${action.id}: ${e.message}")
            false // Transient — retry
        }
    }

    // ─────────────────────────────────────────────────────
    // APPLY — POST /api/v1/applications
    // ─────────────────────────────────────────────────────

    private suspend fun syncApply(action: OfflinePendingActionEntity): Boolean {
        val coverNote = offlineActionQueue.extractCoverNote(payloadJson = action.payloadJson)

        val response = applicationsApiService.createApplication(
            jobId   = action.jobId,
            request = ApplicationRequest(message = coverNote)
        )

        return if (response.isSuccessful) {
            // Confirm the applied state in Room
            jobDao.updateAppliedStatus(action.jobId, true)
            true
        } else {
            val code = response.code()
            // 400 = already applied (idempotent — treat as success)
            // 403 = not an employee — permanent failure, treat as success to remove
            // 4xx  = permanent failure, remove from queue
            // 5xx  = transient, retry
            code in 400..499
        }
    }

    // ─────────────────────────────────────────────────────
    // SAVE / UNSAVE — local-only in current backend
    // (no server endpoint exists yet — just confirm Room state)
    // ─────────────────────────────────────────────────────

    private suspend fun syncSave(jobId: String, saved: Boolean): Boolean {
        // The Room state was already set optimistically in JobRepository.
        // This is a no-op for now but keeps the queue contract clean.
        // When a server save-job endpoint is added, do the API call here.
        jobDao.updateSavedStatus(jobId, saved)
        return true
    }
}