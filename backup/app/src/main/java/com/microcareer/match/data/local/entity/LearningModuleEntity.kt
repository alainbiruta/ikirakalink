package com.microcareer.match.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.microcareer.match.data.local.converter.ListConverters

@Entity(tableName = "learning_modules")
@TypeConverters(ListConverters::class)
data class LearningModuleEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val skillName: String,
    val duration: Int, // in minutes
    val contentUrl: String?,
    val contentType: String, // VIDEO, ARTICLE, INTERACTIVE
    val difficulty: String, // BEGINNER, INTERMEDIATE, ADVANCED
    val prerequisites: List<String> = emptyList(),
    val isCompleted: Boolean = false,
    val progress: Int = 0, // 0-100
    val recommendationScore: Float = 0f,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val isSynced: Boolean = false
)
