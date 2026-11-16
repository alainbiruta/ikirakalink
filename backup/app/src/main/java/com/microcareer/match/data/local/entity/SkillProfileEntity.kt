package com.microcareer.match.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skill_profiles")
data class SkillProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val skillName: String,
    val proficiencyLevel: String, // BEGINNER, INTERMEDIATE, EXPERT
    val yearsOfExperience: Int = 0,
    val sourceType: String, // MANUAL, VOICE, QUIZ, VERIFIED
    val rawInput: String? = null,
    val confidence: Float = 0f,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false
)
