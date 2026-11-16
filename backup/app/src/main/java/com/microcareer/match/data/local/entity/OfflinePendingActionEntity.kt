package com.microcareer.match.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents an action taken while offline that must be synced when back online.
 *
 * Supported action types (ActionType constants below):
 *   APPLY_FOR_JOB  — user applied while offline
 *   SAVE_JOB       — user saved a job while offline
 *   UNSAVE_JOB     — user unsaved a job while offline
 *
 * Keep this table append-only. SyncWorker deletes rows after successful sync.
 */
@Entity(tableName = "offline_pending_actions")
data class OfflinePendingActionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /** One of the ActionType constants */
    val actionType: String,

    /** The job ID this action targets */
    val jobId: String,

    /**
     * Optional payload serialised as JSON string.
     * For APPLY_FOR_JOB: {"cover_note": "..."} or null
     * For SAVE/UNSAVE: null
     */
    val payloadJson: String? = null,

    /** Epoch millis when the action was queued */
    val createdAt: Long = System.currentTimeMillis(),

    /**
     * How many times the sync worker has tried and failed.
     * After MAX_RETRIES the row is deleted to prevent infinite loops.
     */
    val retryCount: Int = 0
) {
    companion object {
        const val ACTION_APPLY_FOR_JOB = "APPLY_FOR_JOB"
        const val ACTION_SAVE_JOB      = "SAVE_JOB"
        const val ACTION_UNSAVE_JOB    = "UNSAVE_JOB"

        /** Drop an action after this many failed sync attempts */
        const val MAX_RETRIES = 3
    }
}