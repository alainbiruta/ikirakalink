package com.microcareer.match.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.microcareer.match.data.local.entity.LearningModuleEntity

/**
 * Remote DTO — mirrors LearningModule.to_dict() from the backend.
 *
 * Field names use @SerializedName to map camelCase JSON keys
 * (returned by the Python backend) to Kotlin properties.
 */
data class LearningModuleDto(

    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("skillName")
    val skillName: String,

    @SerializedName("duration")
    val duration: Int,                      // minutes

    @SerializedName("contentUrl")
    val contentUrl: String?,

    @SerializedName("contentType")
    val contentType: String,                // VIDEO | ARTICLE | INTERACTIVE

    @SerializedName("difficulty")
    val difficulty: String,                 // BEGINNER | INTERMEDIATE | ADVANCED

    @SerializedName("prerequisites")
    val prerequisites: List<String> = emptyList(),

    @SerializedName("recommendationScore")
    val recommendationScore: Float = 0f,

    @SerializedName("createdAt")
    val createdAt: Long = System.currentTimeMillis(),

    @SerializedName("isSynced")
    val isSynced: Boolean = true,

    // ── Progress fields (injected per-request by backend) ──
    @SerializedName("userStatus")
    val userStatus: String = "not_started", // not_started | in_progress | completed

    @SerializedName("progressPercent")
    val progressPercent: Int = 0,

    @SerializedName("completedAt")
    val completedAt: Long? = null,
)

// ── DTO → Room Entity ──────────────────────────────────────

fun LearningModuleDto.toEntity(): LearningModuleEntity = LearningModuleEntity(
    id                  = id,
    title               = title,
    description         = description,
    skillName           = skillName,
    duration            = duration,
    contentUrl          = contentUrl,
    contentType         = contentType,
    difficulty          = difficulty,
    prerequisites       = prerequisites,
    recommendationScore = recommendationScore,
    createdAt           = createdAt,
    isSynced            = isSynced,
    // Merge server-side progress into entity
    isCompleted         = userStatus == "completed",
    progress            = if (userStatus == "completed") 100 else progressPercent,
    completedAt         = completedAt,
)