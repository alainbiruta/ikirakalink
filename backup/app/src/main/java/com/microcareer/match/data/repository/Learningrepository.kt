package com.microcareer.match.data.repository

import com.microcareer.match.data.local.entity.LearningModuleEntity
import com.microcareer.match.domain.model.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Repository for learning module content.
 *
 * Conventions:
 * - Flow<Resource<T>> → observable streams (auto-updates from Room)
 * - suspend Resource<T> → one-shot operations (sync, update progress)
 */
interface LearningRepository {

    /* ── Queries (observable) ───────────────────────────────── */

    /**
     * All learning modules ordered by recommendation score.
     * Used for main learning screen when no filters are active.
     */
    fun getAllModules(): Flow<Resource<List<LearningModuleEntity>>>

    /**
     * Modules for a specific skill.
     * Used when user taps "Learn X" from skill gap recommendation.
     */
    fun getModulesForSkill(skillName: String): Flow<Resource<List<LearningModuleEntity>>>

    /**
     * Top recommended modules based on user's skill gaps.
     * Used for personalized learning feed.
     */
    fun getRecommendedModules(limit: Int = 10): Flow<Resource<List<LearningModuleEntity>>>

    /**
     * Modules user has completed.
     * Used for "My Learning" → "Completed" tab.
     */
    fun getCompletedModules(): Flow<Resource<List<LearningModuleEntity>>>

    /**
     * Single module by ID.
     * Used for module detail screen.
     */
    fun getModule(moduleId: String): Flow<Resource<LearningModuleEntity?>>

    /**
     * Full-text search across title, description, skill.
     * Used for search bar in learning screen.
     */
    fun searchModules(query: String): Flow<Resource<List<LearningModuleEntity>>>

    /* ── Mutations (one-shot suspends) ──────────────────────── */

    /**
     * Records progress for a module (0-100%).
     * Automatically marks as completed when progress reaches 100.
     * Syncs to backend in background via WorkManager.
     */
    suspend fun updateProgress(moduleId: String, progress: Int): Resource<Unit>

    /**
     * Fetches latest modules from backend and upserts into Room.
     * The observable flows above will emit automatically after sync.
     * Called by:
     * - Pull-to-refresh in learning screen
     * - Periodic background sync (WorkManager)
     */
    suspend fun refreshModules(): Resource<Unit>
}