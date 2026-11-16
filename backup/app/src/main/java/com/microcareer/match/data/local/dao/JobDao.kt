package com.microcareer.match.data.local.dao

import androidx.room.*
import com.microcareer.match.data.local.entity.JobEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs")
    suspend fun getAllJobs(): List<JobEntity>

    @Query("SELECT * FROM jobs WHERE status = 'OPEN' ORDER BY matchScore DESC, createdAt DESC")
    fun getAllAvailableJobs(): Flow<List<JobEntity>>

    @Query("SELECT * FROM jobs WHERE id = :jobId")
    fun getJob(jobId: String): Flow<JobEntity?>

    @Query("SELECT * FROM jobs WHERE id = :jobId")
    suspend fun getJobOnce(jobId: String): JobEntity?

    @Query("SELECT * FROM jobs WHERE isSaved = 1 ORDER BY updatedAt DESC")
    fun getSavedJobs(): Flow<List<JobEntity>>

    @Query("SELECT * FROM jobs WHERE isApplied = 1 ORDER BY updatedAt DESC")
    fun getAppliedJobs(): Flow<List<JobEntity>>

    @Query("SELECT * FROM jobs WHERE category = :category AND status = 'OPEN' ORDER BY matchScore DESC")
    fun getJobsByCategory(category: String): Flow<List<JobEntity>>

    /**
     * Full-text search across title, employerName, category, and location.
     * LIKE is case-insensitive for ASCII in SQLite by default.
     * Results ordered by matchScore so best fits appear first.
     */
    @Query("""
        SELECT * FROM jobs
        WHERE status = 'OPEN'
          AND (
               title        LIKE '%' || :query || '%'
            OR employerName LIKE '%' || :query || '%'
            OR category     LIKE '%' || :query || '%'
            OR location     LIKE '%' || :query || '%'
          )
        ORDER BY matchScore DESC, createdAt DESC
    """)
    fun searchJobs(query: String): Flow<List<JobEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<JobEntity>)

    @Update
    suspend fun updateJob(job: JobEntity)

    @Query("UPDATE jobs SET isSaved = :saved, updatedAt = :timestamp WHERE id = :jobId")
    suspend fun updateSavedStatus(
        jobId     : String,
        saved     : Boolean,
        timestamp : Long = System.currentTimeMillis()
    )

    @Query("UPDATE jobs SET isApplied = :applied, updatedAt = :timestamp WHERE id = :jobId")
    suspend fun updateAppliedStatus(
        jobId     : String,
        applied   : Boolean,
        timestamp : Long = System.currentTimeMillis()
    )

    @Query("UPDATE jobs SET isSynced = :synced WHERE id = :jobId")
    suspend fun updateSyncStatus(jobId: String, synced: Boolean)

    @Query("SELECT * FROM jobs WHERE isSynced = 0")
    suspend fun getUnsyncedJobs(): List<JobEntity>

    @Query("DELETE FROM jobs WHERE id = :jobId")
    suspend fun deleteJob(jobId: String)

    @Query("DELETE FROM jobs WHERE status != 'APPLIED' AND updatedAt < :timestamp")
    suspend fun deleteOldJobs(timestamp: Long)
    // Get jobs belonging to a specific employer (for offline fallback)
    @Query("SELECT * FROM jobs WHERE employerId = :employerId")
    suspend fun getJobsByEmployer(employerId: String): List<JobEntity>

    // Update job status field
    @Query("UPDATE jobs SET status = :status WHERE id = :jobId")
    suspend fun updateJobStatus(jobId: String, status: String)
    @Query("DELETE FROM jobs WHERE isApplied = 0")
    suspend fun deleteAllJobs()
    @Transaction
    suspend fun replaceAllJobs(jobs: List<JobEntity>) {
        deleteAllJobs()
        insertJobs(jobs)
    }
    // Delete a single job by id
    @Query("DELETE FROM jobs")
    suspend fun clearAllJobs()
    @Query("UPDATE jobs SET matchScore = :score WHERE id = :jobId")
    suspend fun updateMatchScore(jobId: String, score: Float)

}
