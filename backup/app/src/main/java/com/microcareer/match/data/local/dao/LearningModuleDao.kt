package com.microcareer.match.data.local.dao

import androidx.room.*
import com.microcareer.match.data.local.entity.LearningModuleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LearningModuleDao {
    @Query("SELECT * FROM learning_modules ORDER BY recommendationScore DESC, createdAt DESC")
    fun getAllLearningModules(): Flow<List<LearningModuleEntity>>

    @Query("SELECT * FROM learning_modules WHERE isCompleted = 0 ORDER BY recommendationScore DESC LIMIT :limit")
    fun getRecommendedModules(limit: Int = 10): Flow<List<LearningModuleEntity>>

    @Query("SELECT * FROM learning_modules WHERE id = :moduleId")
    fun getModule(moduleId: String): Flow<LearningModuleEntity?>

    @Query("SELECT * FROM learning_modules WHERE skillName = :skillName")
    fun getModulesForSkill(skillName: String): Flow<List<LearningModuleEntity>>

    @Query("SELECT * FROM learning_modules WHERE isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedModules(): Flow<List<LearningModuleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModule(module: LearningModuleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModules(modules: List<LearningModuleEntity>)

    @Update
    suspend fun updateModule(module: LearningModuleEntity)

    @Query("UPDATE learning_modules SET progress = :progress WHERE id = :moduleId")
    suspend fun updateProgress(moduleId: String, progress: Int)

    @Query("UPDATE learning_modules SET isCompleted = 1, completedAt = :completedAt WHERE id = :moduleId")
    suspend fun markAsCompleted(moduleId: String, completedAt: Long = System.currentTimeMillis())
    @Query("UPDATE learning_modules SET isSynced = :synced WHERE id = :moduleId")
    suspend fun updateSyncStatus(moduleId: String, synced: Boolean)
    @Query("SELECT * FROM learning_modules WHERE isSynced = 0")
    suspend fun getUnsyncedModules(): List<LearningModuleEntity>

    @Query("DELETE FROM learning_modules WHERE id = :moduleId")
    suspend fun deleteModule(moduleId: String)
}
