package com.microcareer.match.domain.repository

import com.microcareer.match.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getUserProfile(): Flow<UserProfile>
    suspend fun saveSkills(skills: List<String>)
    suspend fun savePersonalInfo(name: String, phone: String, location: String)
    suspend fun saveBio(bio: String)
    suspend fun logout()
}