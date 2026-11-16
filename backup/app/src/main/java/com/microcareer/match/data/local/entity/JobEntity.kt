package com.microcareer.match.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.microcareer.match.data.local.converter.ListConverters

@Entity(tableName = "jobs")
@TypeConverters(ListConverters::class)
data class JobEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val employerId: String,
    val employerName: String?,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val requiredSkills: List<String>,
    val payment: Double,
    val currency: String = "RWF",
    val duration: Long, // e.g., "2 hours", "1 day"
    val category: String,
    val matchScore: Float,
    val status: String = "OPEN", // OPEN, APPLIED, IN_PROGRESS, COMPLETED, CLOSED
    val deadline: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isSaved: Boolean = false,
    val isApplied: Boolean = false,
    val isSynced: Boolean = false
)
