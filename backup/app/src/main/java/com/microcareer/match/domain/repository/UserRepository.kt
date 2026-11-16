package com.microcareer.match.domain.repository

import com.microcareer.match.data.local.entity.UserEntity
import com.microcareer.match.data.repository.AppResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getProfile(): Flow<AppResult<UserEntity>>
    fun observeCurrentUser(): Flow<UserEntity?>
    suspend fun saveSkills(skills: List<String>)
}