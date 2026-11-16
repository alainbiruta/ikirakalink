package com.microcareer.match.data.local.dao

import androidx.room.*
import com.microcareer.match.data.local.entity.OfflinePendingActionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OfflinePendingActionDao {

    /** Insert a new pending action. Returns the new row id. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(action: OfflinePendingActionEntity): Long

    /** All pending actions ordered by creation time (oldest first = FIFO) */
    @Query("SELECT * FROM offline_pending_actions ORDER BY createdAt ASC")
    suspend fun getAll(): List<OfflinePendingActionEntity>

    /** Reactive version — UI can show pending count if desired */
    @Query("SELECT COUNT(*) FROM offline_pending_actions")
    fun observePendingCount(): Flow<Int>

    /** Delete a row after it synced successfully */
    @Query("DELETE FROM offline_pending_actions WHERE id = :id")
    suspend fun deleteById(id: Long)

    /** Increment retry count after a transient failure */
    @Query("""
        UPDATE offline_pending_actions
        SET retryCount = retryCount + 1
        WHERE id = :id
    """)
    suspend fun incrementRetry(id: Long)

    /** Remove rows that have hit the retry ceiling — called at start of each sync */
    @Query("""
        DELETE FROM offline_pending_actions
        WHERE retryCount >= ${OfflinePendingActionEntity.MAX_RETRIES}
    """)
    suspend fun deleteExhausted()

    /** Wipe everything — used on logout */
    @Query("DELETE FROM offline_pending_actions")
    suspend fun clearAll()
}