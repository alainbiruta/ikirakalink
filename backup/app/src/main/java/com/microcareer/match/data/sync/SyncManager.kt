package com.microcareer.match.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.microcareer.match.data.local.dao.JobDao
import com.microcareer.match.data.local.dao.LearningModuleDao
import com.microcareer.match.data.local.dao.SkillProfileDao
import com.microcareer.match.data.local.entity.JobEntity
import com.microcareer.match.data.local.entity.LearningModuleEntity
import com.microcareer.match.data.remote.api.MicroCareerApiService
import com.microcareer.match.data.remote.dto.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@HiltWorker  // ✅ ADDED: This annotation is required for Hilt + WorkManager
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val apiService: MicroCareerApiService,
    private val firebaseAuth: FirebaseAuth,
    private val jobDao: JobDao,
    private val learningModuleDao: LearningModuleDao,
    private val skillProfileDao: SkillProfileDao
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val firebaseUser = firebaseAuth.currentUser
                ?: return@withContext Result.success()

            val tokenResult = firebaseUser.getIdToken(false).await()
            val idToken = tokenResult.token
                ?: return@withContext Result.retry()

            // Gather unsynced local data
            val unsyncedJobs = jobDao.getUnsyncedJobs()
            val unsyncedLearning = learningModuleDao.getUnsyncedModules()
            val unsyncedSkills = skillProfileDao.getUnsyncedSkills()

            val syncRequest = SyncRequest(
                unsyncedJobs = unsyncedJobs.map { it.id },
                unsyncedLearning = unsyncedLearning.map { it.id },
                unsyncedSkills = unsyncedSkills.map {
                    SkillProfileSync(
                        skillName = it.skillName,
                        proficiencyLevel = it.proficiencyLevel,
                        sourceType = it.sourceType,
                        rawInput = it.rawInput
                    )
                },
                lastSyncTimestamp = inputData.getLong(KEY_LAST_SYNC, 0L)
            )

            val response = apiService.syncData(
                "Bearer $idToken",
                syncRequest
            )

            if (!response.isSuccessful || response.body()?.success != true) {
                return@withContext retryOrFail()
            }

            val syncResponse = response.body()!!.data
                ?: return@withContext Result.success()

            // Insert new server data safely
            syncResponse.newJobs.forEach { jobDto ->
                jobDao.insertJob(jobDto.toEntity())
            }

            syncResponse.newLearningModules.forEach { moduleDto ->
                learningModuleDao.insertModule(moduleDto.toEntity())
            }

            // Mark synced items
            unsyncedJobs.forEach { jobDao.updateSyncStatus(it.id, true) }
            unsyncedLearning.forEach { learningModuleDao.updateSyncStatus(it.id, true) }
            unsyncedSkills.forEach { skillProfileDao.updateSyncStatus(it.id, true) }

            val outputData = workDataOf(
                KEY_LAST_SYNC to syncResponse.syncTimestamp
            )

            Result.success(outputData)

        } catch (e: Exception) {
            retryOrFail()
        }
    }

    private fun retryOrFail(): Result {
        return if (runAttemptCount < MAX_RETRY_COUNT) {
            Result.retry()
        } else {
            Result.failure()
        }
    }

    companion object {
        const val WORK_NAME = "sync_work"
        const val KEY_LAST_SYNC = "last_sync_timestamp"
        private const val MAX_RETRY_COUNT = 3
    }
}

class SyncManager(private val context: Context) {

    fun schedulePeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val request = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES,
            5, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                SyncWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }

    fun triggerImmediateSync() {
        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "${SyncWorker.WORK_NAME}_immediate",
                ExistingWorkPolicy.REPLACE,
                request
            )
    }

    fun cancelSync() {
        WorkManager.getInstance(context)
            .cancelUniqueWork(SyncWorker.WORK_NAME)
    }
}

private fun JobDto.toEntity() = JobEntity(
    id = id,
    title = title,
    description = description,
    employerId = employerId,
    employerName = employerName,
    location = location,
    latitude = latitude,
    longitude = longitude,
    requiredSkills = requiredSkills,
    payment = payment,
    currency = currency,
    duration = duration.toLongOrNull() ?: 0L,
    category = category,
    matchScore = matchScore,
    status = status,
    deadline = deadline,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isSynced = true
)

private fun LearningModuleDto.toEntity() = LearningModuleEntity(
    id = id,
    title = title,
    description = description,
    skillName = skillName,
    duration = duration,
    contentUrl = contentUrl,
    contentType = contentType,
    difficulty = difficulty,
    prerequisites = prerequisites,
    recommendationScore = recommendationScore,
    createdAt = createdAt,
    isSynced = true
)