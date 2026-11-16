package com.microcareer.match.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.microcareer.match.data.local.converter.ListConverters

@Entity(tableName = "users")
@TypeConverters(ListConverters::class)
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val fullName: String,
    val role: String?,
    val phoneNumber: String? = null,
    val profilePictureUrl: String? = null,
    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val availableHours: String?,
    val companyName: String?,
    val rating: Float = 0f,
    val completedJobs: Int = 0,
    val skills: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false,
    val bio: String? = null
)
