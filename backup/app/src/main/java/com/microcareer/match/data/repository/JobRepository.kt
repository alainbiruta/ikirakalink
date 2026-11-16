package com.microcareer.match.data.repository

import android.util.Log
import com.microcareer.match.data.local.OfflineActionQueue
import com.microcareer.match.data.local.TokenManager
import com.microcareer.match.data.local.dao.JobDao
import com.microcareer.match.data.local.entity.JobEntity
import com.microcareer.match.data.remote.api.ApplicationsApiService
import com.microcareer.match.data.remote.api.JobsApiService
import com.microcareer.match.data.remote.models.PostJobRequest
import com.microcareer.match.data.remote.models.RemoteJob
import com.microcareer.match.data.remote.models.RemoteApplication
import com.microcareer.match.presentation.employer.ApplicantUiModel
import com.microcareer.match.data.remote.models.ApplicationRequest
import com.microcareer.match.utils.NetworkMonitor
import com.microcareer.match.utils.networkErrorMessage
import com.microcareer.match.worker.SyncScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow


private const val TAG = "JobRepository"
@Singleton
class JobRepository @Inject constructor(
    private val jobsApiService         : JobsApiService,
    private val applicationsApiService : ApplicationsApiService,
    private val jobDao                 : JobDao,
    private val offlineActionQueue     : OfflineActionQueue,
    private val networkMonitor         : NetworkMonitor,
    private val syncScheduler          : SyncScheduler,
    private val tokenManager           : TokenManager
) {
    private val _jobsChanged = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val jobsChanged: SharedFlow<Unit> = _jobsChanged.asSharedFlow()
    // ══════════════════════════════════════════════════════
    // GET JOBS
    // ══════════════════════════════════════════════════════



    fun getJobs(
        skip     : Int     = 0,
        limit    : Int     = 20,
        category : String? = null,
        location : String? = null
    ): Flow<AppResult<List<JobEntity>>> = flow {
        Log.d("JOBS_DEBUG", "▶ getJobs() called, skip=$skip, thread=${Thread.currentThread().name}")
        emit(AppResult.Loading)

        val cached = jobDao.getAllJobs()
        Log.d("JOBS_DEBUG", "▶ cached=${cached.size}")
        if (cached.isNotEmpty()) {
            emit(AppResult.Success(cached, isFromCache = true))
        }

        try {
            val response = jobsApiService.getJobs(skip = skip, limit = limit, category = category, location = location)
            Log.d("JOBS_DEBUG", "▶ API response code=${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                val fresh = response.body()!!.data.jobs.map { it.toEntity() }

                Log.d("JOBS_DEBUG", "▶ entities=${fresh.size}, about to replaceAllJobs")

                // Preserve isSaved / isApplied already stored in DB
                val existing = jobDao.getAllJobs().associateBy { it.id }
                Log.d("APPLIED_DEBUG", "fresh job ids: ${fresh.map { it.id }}")
                Log.d("APPLIED_DEBUG", "existing applied: ${existing.values.filter { it.isApplied }.map { it.id }}")

                val merged = fresh.map { entity ->
                    val old = existing[entity.id]
                    if (old != null) entity.copy(isSaved = old.isSaved, isApplied = old.isApplied)
                    else entity
                }

                if (skip == 0) jobDao.deleteAllJobs() // now safe — only deletes non-applied rows
                jobDao.insertJobs(merged)
                val freshIds = fresh.map { it.id }.toSet()
                val appliedNotInFresh = existing.values.filter { it.isApplied && it.id !in freshIds }
                if (appliedNotInFresh.isNotEmpty()) {
                    jobDao.insertJobs(appliedNotInFresh)
                }
                Log.d("JOBS_DEBUG", "▶ emitting Success")
                emit(AppResult.Success(merged))
                Log.d("JOBS_DEBUG", "▶ flow done")
            } else {
                Log.d("JOBS_DEBUG", "▶ API not successful, cached=${ cached.size}")
                if (cached.isEmpty()) emit(AppResult.Error("Could not load jobs (${response.code()})"))
            }
        } catch (e: Exception) {
            Log.e("JOBS_DEBUG", "▶ exception: ${e.message}")
            if (cached.isEmpty()) emit(AppResult.Error(networkErrorMessage(e)))
        }
    }

    // ══════════════════════════════════════════════════════
    // GET SINGLE JOB
    // ══════════════════════════════════════════════════════

    fun getJobById(jobId: String): Flow<AppResult<JobEntity>> = flow {
        emit(AppResult.Loading)

        val cached = jobDao.getJobOnce(jobId)
        if (cached != null) {
            emit(AppResult.Success(cached, isFromCache = true))
        }

        try {
            val response = jobsApiService.getJob(jobId)
            if (response.isSuccessful && response.body()?.data != null) {
                val entity = response.body()!!.data!!.toEntity()
                jobDao.insertJob(entity)
                emit(AppResult.Success(entity))
            } else {
                if (cached == null) emit(AppResult.Error("Job not found"))
            }
        } catch (e: Exception) {
            if (cached == null) emit(AppResult.Error(networkErrorMessage(e)))
        }
    }

    // ══════════════════════════════════════════════════════
    // AI RECOMMENDATIONS
    // ══════════════════════════════════════════════════════

    fun getRecommendations(): Flow<AppResult<List<JobEntity>>> = flow {
        emit(AppResult.Loading)

        val cached = jobDao.getAllJobs().sortedByDescending { it.matchScore }
        if (cached.isNotEmpty()) {
            emit(AppResult.Success(cached.take(10), isFromCache = true))
        }

        try {
            val response = jobsApiService.getRecommendations()  // ← was missing
            if (response.isSuccessful && response.body()?.data != null) {
                @Suppress("UNCHECKED_CAST")
                val matches = response.body()!!.data!!["recommendations"] as? List<Map<String, Any>>
                Log.d("REC_DEBUG", "recommendations raw: ${response.body()!!.data}")
                Log.d("REC_DEBUG", "matches count: ${matches?.size}, first: ${matches?.firstOrNull()}")

                if (!matches.isNullOrEmpty()) {
                    Log.d(TAG, "✅ Got ${matches.size} recommendations")

                    // log keys once to confirm field name
                    matches.firstOrNull()?.let { Log.d(TAG, "match keys: ${it.keys}") }

                    matches.forEach { match ->
                        val jobId = (match["id"] ?: match["job_id"]) as? String ?: return@forEach
                        val score = when (val s = match["score"] ?: match["match_score"]) {
                            is Double -> s.toFloat()
                            is Float  -> s
                            is Int    -> s.toFloat() / 100f
                            else      -> return@forEach
                        }
                        jobDao.updateMatchScore(jobId, score)
                        Log.d(TAG, "✅ matchScore updated: $jobId → $score")
                    }

                    val allJobs        = jobDao.getAllJobs()
                    val recommendedIds = matches.mapNotNull { (it["id"] ?: it["job_id"]) as? String }.toSet()
                    val recommended    = allJobs.filter { it.id in recommendedIds }
                        .sortedByDescending { it.matchScore }
                    emit(AppResult.Success(recommended))
                } else {
                    if (cached.isEmpty()) emit(AppResult.Success(emptyList()))
                }
            } else {
                Log.e(TAG, "Recommendations failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Recommendations exception: ${e.message}")
        }
    }

    // ══════════════════════════════════════════════════════
    // SAVE / UNSAVE
    // ══════════════════════════════════════════════════════

    suspend fun saveJob(jobId: String) {
        jobDao.updateSavedStatus(jobId, true)
        if (!networkMonitor.isCurrentlyOnline()) {
            offlineActionQueue.enqueueSave(jobId)
            syncScheduler.scheduleSyncOnConnectivity()
            Log.d(TAG, "Save queued offline: $jobId")
        }
    }
    suspend fun fetchMyApplicationsRaw(): List<Map<String, Any>>? {
        return try {
            val response = applicationsApiService.getMyApplications(page = 1, pageSize = 50)
            if (!response.isSuccessful || response.body() == null) return null
            @Suppress("UNCHECKED_CAST")
            val data = response.body()!!.data as? Map<String, Any> ?: return null
            data["applications"] as? List<Map<String, Any>>
        } catch (e: Exception) {
            Log.e(TAG, "fetchMyApplicationsRaw: ${e.message}")
            null
        }
    }

    suspend fun unsaveJob(jobId: String) {
        jobDao.updateSavedStatus(jobId, false)
        if (!networkMonitor.isCurrentlyOnline()) {
            offlineActionQueue.enqueueUnsave(jobId)
            syncScheduler.scheduleSyncOnConnectivity()
            Log.d(TAG, "Unsave queued offline: $jobId")
        }
    }

    fun getSavedJobs(): Flow<List<JobEntity>> = jobDao.getSavedJobs()

    // ══════════════════════════════════════════════════════
    // APPLY FOR JOB
    // ══════════════════════════════════════════════════════

    fun applyForJob(jobId: String, coverNote: String? = null): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)

        if (!networkMonitor.isCurrentlyOnline()) {
            jobDao.updateAppliedStatus(jobId, true)
            offlineActionQueue.enqueueApply(jobId, coverNote)
            syncScheduler.scheduleSyncOnConnectivity()
            emit(AppResult.Success(true, isFromCache = true))
            return@flow
        }

        try {
            val response = applicationsApiService.createApplication(
                jobId   = jobId,
                request = ApplicationRequest(message = coverNote)
            )
            if (response.isSuccessful) {
                jobDao.updateAppliedStatus(jobId, true)
                jobDao.updateSavedStatus(jobId, true)
                emit(AppResult.Success(true))
            } else {
                when (response.code()) {
                    400 -> {
                        // Already applied on server — make sure Room reflects this
                        jobDao.updateAppliedStatus(jobId, true)
                        jobDao.updateSavedStatus(jobId, true)
                        emit(AppResult.Error("Already applied for this job"))
                    }
                    403 -> emit(AppResult.Error("Only employees can apply"))
                    else -> emit(AppResult.Error("Application failed (${response.code()})"))
                }
            }
        } catch (e: Exception) {
            jobDao.updateAppliedStatus(jobId, true)
            offlineActionQueue.enqueueApply(jobId, coverNote)
            syncScheduler.scheduleSyncOnConnectivity()
            emit(AppResult.Success(true, isFromCache = true))
        }
    }

    // ══════════════════════════════════════════════════════
    // SEARCH  (local Room — returns Flow<List<JobEntity>>)
    // ══════════════════════════════════════════════════════

    fun searchJobs(query: String): Flow<List<JobEntity>> = jobDao.searchJobs(query)

    fun getJobsByCategory(category: String): Flow<List<JobEntity>> =
        jobDao.getJobsByCategory(category)

    fun getAppliedJobs(): Flow<List<JobEntity>> = jobDao.getAppliedJobs()
// Add this new function inside JobRepository, after getAppliedJobs()

    suspend fun syncAppliedJobsFromServer() {
        if (!networkMonitor.isCurrentlyOnline()) return
        try {
            val response = applicationsApiService.getMyApplications(page = 1, pageSize = 50)
            if (!response.isSuccessful || response.body() == null) return

            @Suppress("UNCHECKED_CAST")
            val data = response.body()!!.data as? Map<String, Any> ?: return
            val applications = data["applications"] as? List<Map<String, Any>> ?: return

            for (app in applications) {
                val jobMap  = app["job"] as? Map<String, Any> ?: continue
                val jobId   = jobMap["id"] as? String ?: continue

                val existing = jobDao.getJobOnce(jobId)
                if (existing != null) {
                    // Job already in Room — just mark it applied
                    jobDao.updateAppliedStatus(jobId, true)
                } else {
                    // Job not in Room (applied from another device/session) — insert a stub
                    val stubJob = JobEntity(
                        id             = jobId,
                        title          = jobMap["title"] as? String ?: "",
                        description    = "",
                        category       = jobMap["category"] as? String ?: "",
                        location       = jobMap["location"] as? String ?: "",
                        payment        = (jobMap["payment"] as? Number)?.toDouble() ?: 0.0,
                        currency       = jobMap["currency"] as? String ?: "RWF",
                        latitude       = 0.0,
                        longitude      = 0.0,
                        requiredSkills = emptyList(),
                        duration       = 0L,
                        deadline       = null,
                        status         = jobMap["status"] as? String ?: "OPEN",
                        employerId     = "",
                        employerName   = jobMap["employerName"] as? String,
                        matchScore     = 0f,
                        isSaved        = true,
                        isApplied      = true,
                        isSynced       = true,
                        createdAt      = System.currentTimeMillis(),
                        updatedAt      = System.currentTimeMillis()
                    )
                    jobDao.insertJob(stubJob)
                }
            }
            Log.d(TAG, "✅ syncAppliedJobsFromServer: synced ${applications.size} applied jobs")
        } catch (e: Exception) {
            Log.e(TAG, "syncAppliedJobsFromServer exception: ${e.message}")
        }
    }
    fun observePendingSyncCount(): Flow<Int> = offlineActionQueue.observePendingCount()

    // ══════════════════════════════════════════════════════
    // EMPLOYER — GET MY JOBS
    // ══════════════════════════════════════════════════════

    fun getEmployerJobs(): Flow<AppResult<List<JobEntity>>> = flow {
        emit(AppResult.Loading)
        try {
            val response = jobsApiService.getEmployerJobs()
            if (response.isSuccessful && response.body() != null) {
                val jobs = response.body()!!.data.jobs
                    .map { it.toEntity() }
                    .sortedByDescending { it.createdAt }
                val serverJobIds = jobs.map { it.id }.toSet()
                val userId = tokenManager.getUserId() ?: ""
                val localJobs = jobDao.getJobsByEmployer(userId)
                localJobs.filter { it.id !in serverJobIds }.forEach { jobDao.deleteJob(it.id) }
                // ← unwrap .data.jobs
                jobs.forEach { jobDao.insertJob(it) }
                emit(AppResult.Success(jobs))
            } else {
                val userId = tokenManager.getUserId() ?: ""
                val cached = jobDao.getJobsByEmployer(userId)
                    .sortedByDescending { it.createdAt }
                if (cached.isNotEmpty())
                    emit(AppResult.Success(cached, isFromCache = true))
                else
                    emit(AppResult.Error("Failed to load your jobs (${response.code()})"))
            }
        } catch (e: Exception) {
            val userId = tokenManager.getUserId() ?: ""
            val cached = jobDao.getJobsByEmployer(userId)
                .sortedByDescending { it.createdAt }
            if (cached.isNotEmpty()) emit(AppResult.Success(cached, isFromCache = true))
            else emit(AppResult.Error(networkErrorMessage(e)))
        }
    }

    // ══════════════════════════════════════════════════════
    // EMPLOYER — GET APPLICANTS
    // ══════════════════════════════════════════════════════

    suspend fun getApplicants(): AppResult<List<RemoteApplication>> {
        return try {
            val response = applicationsApiService.getReceivedApplications()
            if (response.isSuccessful && response.body() != null) {
                val applications = response.body()!!.data.applications   // ← unwrap here
                Log.d("JobRepository", "✅ Loaded ${applications.size} applicants from backend")
                AppResult.Success(applications)
            } else {
                Log.e("JobRepository", "getApplicants failed: ${response.code()} ${response.message()}")
                AppResult.Error("Failed to load applicants: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("JobRepository", "getApplicants exception: ${e.message}")
            AppResult.Error(e.message ?: "Unknown error")
        }
    }

    // ══════════════════════════════════════════════════════
    // EMPLOYER — POST JOB
    //
    // CHANGES vs old version:
    //   • duration: Long  →  duration: String  (backend key is "duration", type String)
    //   • Added: isUrgent, requiredHours, maxApplicants, deadline
    // ══════════════════════════════════════════════════════

    suspend fun postJob(
        title         : String,
        description   : String,
        location      : String,
        payment       : Double,
        currency      : String,
        duration      : String,                // FIX: was Long
        category      : String,
        skills        : List<String>,
        isUrgent      : Boolean  = false,      // NEW
        requiredHours : Int      = 0,          // NEW
        maxApplicants : Int      = 0,          // NEW
        deadline      : String?  = null        // NEW — ISO-8601 or null
    ) {
        val request = PostJobRequest(
            title                = title,
            description          = description,
            location             = location,
            payment              = payment,
            currency             = currency,
            duration             = duration,   // FIX: String, key "duration"
            category             = category,
            requiredSkills       = skills,
            isUrgent             = isUrgent,
            requiredHours        = requiredHours,
            maxApplicants        = maxApplicants,
            deadline             = deadline,
            // latitude/longitude: null — employer didn't enter coords in the form.
            // Backend will skip geo-matching for this job; can be added later
            // once a location-picker is wired to the PostJobScreen.
            latitude             = null,
            longitude            = null,
            requiredAvailability = emptyMap()
        )
        val response = jobsApiService.postJob(request)
        if (!response.isSuccessful) {
            throw Exception("Failed to post job: ${response.code()}")
        }
        response.body()?.data?.toEntity()?.let { jobDao.insertJob(it) }
        _jobsChanged.tryEmit(Unit)
    }

    // ══════════════════════════════════════════════════════
    // EMPLOYER — UPDATE JOB
    //
    // CHANGES vs old version:
    //   • duration: Long  →  duration: String
    //   • Added: isUrgent, requiredHours, maxApplicants, deadline
    // ══════════════════════════════════════════════════════

    suspend fun updateJob(
        jobId         : String,
        title         : String,
        description   : String,
        location      : String,
        payment       : Double,
        currency      : String,
        duration      : String,                // FIX: was Long
        category      : String,
        skills        : List<String>,
        isUrgent      : Boolean  = false,      // NEW
        requiredHours : Int      = 0,          // NEW
        maxApplicants : Int      = 0,          // NEW
        deadline      : String?  = null        // NEW
    ) {
        val request = PostJobRequest(
            title                = title,
            description          = description,
            location             = location,
            payment              = payment,
            currency             = currency,
            duration             = duration,   // FIX: String, key "duration"
            category             = category,
            requiredSkills       = skills,
            isUrgent             = isUrgent,
            requiredHours        = requiredHours,
            maxApplicants        = maxApplicants,
            deadline             = deadline,
            latitude             = null,
            longitude            = null,
            requiredAvailability = emptyMap()
        )
        val response = jobsApiService.updateJob(jobId, request)
        if (!response.isSuccessful) {
            throw Exception("Failed to update job: ${response.code()}")

        }
        _jobsChanged.tryEmit(Unit)
    }

    // ══════════════════════════════════════════════════════
    // EMPLOYER — DELETE JOB
    // ══════════════════════════════════════════════════════

    suspend fun deleteJob(jobId: String) {
        val response = jobsApiService.deleteJob(jobId)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete job: ${response.code()}")
        }
        jobDao.deleteJob(jobId)
        // Do NOT emit _jobsChanged here — it triggers getEmployerJobs() which re-fetches
        // the server list and races against the local delete, restoring the deleted job.
        // The ViewModel already removes it from UI state optimistically.
    }
    // ══════════════════════════════════════════════════════
    // EMPLOYER — TOGGLE JOB STATUS
    // ══════════════════════════════════════════════════════

    suspend fun updateJobStatus(jobId: String, status: String) {
        val response = jobsApiService.updateJobStatus(jobId, mapOf("status" to status))
        if (!response.isSuccessful) {
            throw Exception("Failed to update status: ${response.code()}")
        }
        jobDao.updateJobStatus(jobId, status)
        _jobsChanged.tryEmit(Unit)
    }

    // ══════════════════════════════════════════════════════
    // EMPLOYER — UPDATE APPLICATION STATUS
    // ══════════════════════════════════════════════════════

    suspend fun updateApplicationStatus(applicantId: String, jobId: String, status: String) {
        val response = applicationsApiService.updateApplicationStatus(
            applicantId = applicantId,
            jobId       = jobId,
            status      = status
        )
        if (!response.isSuccessful) {
            throw Exception("Failed to update application: ${response.code()}")
        }
    }

}   // ← end of JobRepository class

// ══════════════════════════════════════════════════════════
// RemoteJob → JobEntity   (unchanged)
// ══════════════════════════════════════════════════════════

fun RemoteJob.toEntity(): JobEntity = JobEntity(
    id             = this.id,
    title          = this.title,
    description    = this.description,
    category       = this.category,
    location       = this.location,
    payment        = this.payment,
    currency       = this.currency ?: "RWF",
    latitude       = 0.0,
    longitude      = 0.0,
    requiredSkills = this.requiredSkills ?: emptyList(),
    duration = duration?.toLongOrNull() ?: durationDays?.toLong() ?: 0L,
    deadline       = null,
    status         = status ?: "OPEN",
    employerId     = this.employerId,
    employerName   = this.employerName,
    matchScore     = (matchScore ?: 0.0).toFloat(),
    isSaved        = false,
    isApplied      = this.hasApplied,
    isSynced       = true,
    createdAt      = this.createdAt.toSafeLong(),
    updatedAt      = this.updatedAt.toSafeLong()
)

fun Any?.toSafeLong(): Long {
    return when (this) {
        is Long   -> this
        is Int    -> this.toLong()
        is String -> this.toLongOrNull() ?: System.currentTimeMillis()
        else      -> System.currentTimeMillis()
    }
}