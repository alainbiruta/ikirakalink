package com.microcareer.match.data.local.dao

import androidx.room.*
import com.microcareer.match.data.local.entity.SkillProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillProfileDao {
    @Query("SELECT * FROM skill_profiles WHERE userId = :userId ORDER BY updatedAt DESC")
    fun getUserSkills(userId: String): Flow<List<SkillProfileEntity>>

    @Query("SELECT * FROM skill_profiles WHERE userId = :userId AND skillName = :skillName")
    suspend fun getSkill(userId: String, skillName: String): SkillProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkill(skill: SkillProfileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkills(skills: List<SkillProfileEntity>)

    @Update
    suspend fun updateSkill(skill: SkillProfileEntity)

    @Query("DELETE FROM skill_profiles WHERE id = :skillId")
    suspend fun deleteSkill(skillId: Long)

    @Query("SELECT * FROM skill_profiles WHERE isSynced = 0")
    suspend fun getUnsyncedSkills(): List<SkillProfileEntity>

    @Query("UPDATE skill_profiles SET isSynced = :synced WHERE id = :skillId")
    suspend fun updateSyncStatus(skillId: Long, synced: Boolean)
}
