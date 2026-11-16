package com.microcareer.match.data.repository

import android.util.Log
import com.microcareer.match.data.local.dao.LearningModuleDao
import com.microcareer.match.data.local.entity.LearningModuleEntity
import com.microcareer.match.data.remote.api.LearningApiService
import com.microcareer.match.data.remote.dto.toEntity
import com.microcareer.match.data.remote.dto.LearningModuleDto
import com.microcareer.match.domain.model.Resource
import com.microcareer.match.utils.NetworkMonitor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "LearningRepo"

@Singleton
class LearningRepositoryImpl @Inject constructor(
    private val learningModuleDao : LearningModuleDao,
    private val learningApiService: LearningApiService,
    private val networkMonitor    : NetworkMonitor,
    private val gson              : Gson
) : LearningRepository {

    // ══════════════════════════════════════════════════════
    // FLOW HELPERS
    // ══════════════════════════════════════════════════════

    private fun Flow<List<LearningModuleEntity>>.asResource()
            : Flow<Resource<List<LearningModuleEntity>>> =
        this
            .map<List<LearningModuleEntity>, Resource<List<LearningModuleEntity>>> {
                Resource.Success(it)
            }
            .onStart { emit(Resource.Loading()) }
            .catch { e ->
                Log.e(TAG, "DAO flow error: ${e.message}")
                emit(Resource.Error(e.message ?: "Database error"))
            }

    private fun Flow<LearningModuleEntity?>.asSingleResource()
            : Flow<Resource<LearningModuleEntity?>> =
        this
            .map<LearningModuleEntity?, Resource<LearningModuleEntity?>> {
                Resource.Success(it)
            }
            .onStart { emit(Resource.Loading()) }
            .catch { e ->
                Log.e(TAG, "DAO flow error: ${e.message}")
                emit(Resource.Error(e.message ?: "Database error"))
            }

    // ══════════════════════════════════════════════════════
    // OBSERVABLE QUERIES  (Room is source of truth)
    // ══════════════════════════════════════════════════════

    override fun getAllModules(): Flow<Resource<List<LearningModuleEntity>>> =
        learningModuleDao.getAllLearningModules().asResource()

    override fun getRecommendedModules(limit: Int): Flow<Resource<List<LearningModuleEntity>>> =
        learningModuleDao.getRecommendedModules(limit).asResource()

    override fun getCompletedModules(): Flow<Resource<List<LearningModuleEntity>>> =
        learningModuleDao.getCompletedModules().asResource()

    override fun getModule(moduleId: String): Flow<Resource<LearningModuleEntity?>> =
        learningModuleDao.getModule(moduleId).asSingleResource()

    override fun getModulesForSkill(skillName: String): Flow<Resource<List<LearningModuleEntity>>> =
        learningModuleDao.getModulesForSkill(skillName).asResource()

    override fun searchModules(query: String): Flow<Resource<List<LearningModuleEntity>>> =
        learningModuleDao.getAllLearningModules()
            .map { modules ->
                modules.filter { m ->
                    m.title.contains(query, ignoreCase = true) ||
                            m.description.contains(query, ignoreCase = true) ||
                            m.skillName.contains(query, ignoreCase = true)
                }
            }
            .asResource()

    // ══════════════════════════════════════════════════════
    // REFRESH  — fetch from server, upsert into Room
    // ══════════════════════════════════════════════════════

    override suspend fun refreshModules(): Resource<Unit> {
        if (!networkMonitor.isCurrentlyOnline()) {
            Log.d(TAG, "Offline — skipping refresh, Room data will be used")
            return Resource.Success(Unit)
        }
        return try {
            Log.d(TAG, "Fetching modules from backend…")
            val response = learningApiService.getModules(page = 1, pageSize = 100)
            if (!response.isSuccessful || response.body() == null) {
                Log.e(TAG, "getModules failed: ${response.code()}")
                return Resource.Error("Server returned ${response.code()}")
            }

            val modules = parseModuleList(response.body()!!.data)
            Log.d(TAG, "Received ${modules.size} modules from server")
            learningModuleDao.insertModules(modules.map { it.toEntity() })

            // Also fetch personalised recommendations and update scores in Room
            fetchAndMergeRecommendations()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "refreshModules exception: ${e.message}")
            Resource.Error(e.message ?: "Failed to refresh modules")
        }
    }

    /** Pulls /recommendations/me and updates recommendationScore + progress in Room. */
    private suspend fun fetchAndMergeRecommendations() {
        try {
            val resp = learningApiService.getRecommendations(limit = 50)
            if (!resp.isSuccessful || resp.body() == null) return

            @Suppress("UNCHECKED_CAST")
            val data        = resp.body()!!.data as? Map<String, Any> ?: return
            val recommended = parseModuleList(data["recommended"])
            recommended.forEach { dto ->
                // Update Room entity's progress fields from server state
                val existing = learningModuleDao
                    .getAllLearningModules()   // cold snapshot; we just need IDs
                    .map { list -> list.firstOrNull { it.id == dto.id } }
                val entity = dto.toEntity()
                learningModuleDao.insertModules(listOf(entity)) // REPLACE upsert
            }
            Log.d(TAG, "Merged ${recommended.size} recommendations")
        } catch (e: Exception) {
            Log.w(TAG, "fetchAndMergeRecommendations: ${e.message}")
        }
    }

    // ══════════════════════════════════════════════════════
    // UPDATE PROGRESS
    // ══════════════════════════════════════════════════════

    override suspend fun updateProgress(moduleId: String, progress: Int): Resource<Unit> {
        val valid = progress.coerceIn(0, 100)
        return try {
            // 1. Update Room immediately (optimistic)
            learningModuleDao.updateProgress(moduleId, valid)
            if (valid >= 100) {
                learningModuleDao.markAsCompleted(moduleId, System.currentTimeMillis())
                Log.d(TAG, "Auto-completed module $moduleId")
            }

            // 2. Sync to server if online
            if (networkMonitor.isCurrentlyOnline()) {
                val resp = learningApiService.updateProgress(moduleId, valid)
                Log.d(TAG, "Server progress sync: ${resp.code()}")
            }

            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "updateProgress error: ${e.message}")
            Resource.Error(e.message ?: "Failed to save progress")
        }
    }

    // ══════════════════════════════════════════════════════
    // COMPLETE MODULE  — calls backend to unlock skills
    // ══════════════════════════════════════════════════════

    /**
     * Marks the module complete locally AND calls the backend
     * POST /learning/{moduleId}/complete endpoint which adds
     * the module's skill_name to user.skills server-side.
     *
     * Returns the list of newly unlocked skill names so the
     * ViewModel can show the celebration dialog with exact text.
     */
    suspend fun completeModule(moduleId: String): Resource<List<String>> {
        return try {
            // 1. Mark Room complete immediately
            learningModuleDao.markAsCompleted(moduleId, System.currentTimeMillis())
            learningModuleDao.updateProgress(moduleId, 100)
            Log.d(TAG, "Room marked complete: $moduleId")

            // 2. Call backend (skill unlock happens server-side)
            if (!networkMonitor.isCurrentlyOnline()) {
                Log.w(TAG, "Offline — skill unlock will sync when online")
                return Resource.Success(emptyList())
            }

            val resp = learningApiService.completeModule(moduleId)
            if (!resp.isSuccessful || resp.body() == null) {
                Log.e(TAG, "completeModule server error: ${resp.code()}")
                // Room is already updated — don't surface error to user
                return Resource.Success(emptyList())
            }

            @Suppress("UNCHECKED_CAST")
            val data        = resp.body()!!.data as? Map<String, Any>
            val newSkills   = (data?.get("newSkills") as? List<*>)
                ?.filterIsInstance<String>() ?: emptyList()
            val matchCount  = (data?.get("newMatchCount") as? Number)?.toInt() ?: 0

            Log.d(TAG, "Skills unlocked: $newSkills, new match count: $matchCount")
            Resource.Success(newSkills)

        } catch (e: Exception) {
            Log.e(TAG, "completeModule exception: ${e.message}")
            // Room is already updated — treat as soft success
            Resource.Success(emptyList())
        }
    }

    // ══════════════════════════════════════════════════════
    // GSON PARSE HELPERS
    // ══════════════════════════════════════════════════════

    /**
     * Safely parses the "modules" or "recommended" list from
     * the generic ApiResponse<Any>.data map.
     */
    @Suppress("UNCHECKED_CAST")
    private fun parseModuleList(data: Any?): List<LearningModuleDto> {
        val raw = when (data) {
            is Map<*, *> -> (data as? Map<String, Any>)?.get("modules")
                ?: (data as? Map<String, Any>)?.get("recommended")
                ?: return emptyList()
            is List<*>   -> data
            else         -> return emptyList()
        }
        return try {
            val json = gson.toJson(raw)
            val type = object : TypeToken<List<LearningModuleDto>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "parseModuleList error: ${e.message}")
            emptyList()
        }
    }
}