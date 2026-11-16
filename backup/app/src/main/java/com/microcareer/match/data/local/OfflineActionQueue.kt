package com.microcareer.match.data.local

import android.util.Log
import com.microcareer.match.data.local.dao.OfflinePendingActionDao
import com.microcareer.match.data.local.entity.OfflinePendingActionEntity
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "OfflineActionQueue"

/**
 * Wraps [OfflinePendingActionDao] and provides typed enqueue helpers.
 * Inject this into [JobRepository] — do not inject the DAO directly.
 */
@Singleton
class OfflineActionQueue @Inject constructor(
    private val dao: OfflinePendingActionDao
) {

    // ─────────────────────────────────────────────────────
    // Enqueue helpers (called from JobRepository on network failure)
    // ─────────────────────────────────────────────────────

    suspend fun enqueueApply(jobId: String, coverNote: String?) {
        val payload = coverNote?.let {
            JSONObject().put("cover_note", it).toString()
        }
        val action = OfflinePendingActionEntity(
            actionType  = OfflinePendingActionEntity.ACTION_APPLY_FOR_JOB,
            jobId       = jobId,
            payloadJson = payload
        )
        dao.insert(action)
        Log.d(TAG, "Queued APPLY_FOR_JOB → jobId=$jobId")
    }

    suspend fun enqueueSave(jobId: String) {
        dao.insert(
            OfflinePendingActionEntity(
                actionType = OfflinePendingActionEntity.ACTION_SAVE_JOB,
                jobId      = jobId
            )
        )
        Log.d(TAG, "Queued SAVE_JOB → jobId=$jobId")
    }

    suspend fun enqueueUnsave(jobId: String) {
        dao.insert(
            OfflinePendingActionEntity(
                actionType = OfflinePendingActionEntity.ACTION_UNSAVE_JOB,
                jobId      = jobId
            )
        )
        Log.d(TAG, "Queued UNSAVE_JOB → jobId=$jobId")
    }

    // ─────────────────────────────────────────────────────
    // Read (called from SyncWorker)
    // ─────────────────────────────────────────────────────

    suspend fun getAll(): List<OfflinePendingActionEntity> = dao.getAll()

    /** Reactive count — wire to UI badge if desired */
    fun observePendingCount(): Flow<Int> = dao.observePendingCount()

    // ─────────────────────────────────────────────────────
    // Lifecycle (called from SyncWorker)
    // ─────────────────────────────────────────────────────

    suspend fun markSynced(id: Long) = dao.deleteById(id)

    suspend fun markFailed(id: Long) = dao.incrementRetry(id)

    suspend fun pruneExhausted() = dao.deleteExhausted()

    /** Call on logout */
    suspend fun clearAll() = dao.clearAll()

    // ─────────────────────────────────────────────────────
    // Payload helpers
    // ─────────────────────────────────────────────────────

    fun extractCoverNote(payloadJson: String?): String? {
        if (payloadJson == null) return null
        return try {
            JSONObject(payloadJson).optString("cover_note").takeIf { it.isNotBlank() }
        } catch (e: Exception) {
            null
        }
    }
}