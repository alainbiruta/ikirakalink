package com.microcareer.match.data.repository

import android.util.Log
import com.microcareer.match.data.local.TokenManager
import com.microcareer.match.data.local.dao.UserDao
import com.microcareer.match.data.local.entity.UserEntity
import com.microcareer.match.data.remote.api.AuthApiService
import com.microcareer.match.data.remote.models.SkillsUpdateRequest
import com.microcareer.match.data.remote.networkErrorMessage
import com.microcareer.match.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "UserRepository"

@Singleton
class UserRepository @Inject constructor(
    private val authApiService : AuthApiService,
    private val userDao        : UserDao,
    private val tokenManager   : TokenManager
) : UserRepository {

    // ══════════════════════════════════════════════════════
    // GET PROFILE
    // ══════════════════════════════════════════════════════

    override fun getProfile(): Flow<AppResult<UserEntity>> = flow {
        emit(AppResult.Loading)

        val userId = tokenManager.getUserId()
        if (userId != null) {
            val cached = userDao.getUserOnce(userId)
            if (cached != null) emit(AppResult.Success(cached, isFromCache = true))
        }

        try {
            val response = authApiService.getCurrentUser()
            if (response.isSuccessful && response.body() != null) {
                val entity = response.body()!!.toEntity()
                userDao.insertUser(entity)
                Log.d(TAG, "✅ Profile refreshed from API")
                emit(AppResult.Success(entity))
            } else {
                if (userId == null) emit(AppResult.Error("Could not load profile"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Profile fetch exception: ${e.message}")
            if (userId == null) emit(AppResult.Error(networkErrorMessage(e)))
        }
    }

    // ══════════════════════════════════════════════════════
    // OBSERVE CURRENT USER (reactive)
    // ══════════════════════════════════════════════════════

    override fun observeCurrentUser(): Flow<UserEntity?> {
        val userId = tokenManager.getUserId() ?: return flow { emit(null) }
        return userDao.getUser(userId)
    }

    // ══════════════════════════════════════════════════════
    // SAVE SKILLS
    // ══════════════════════════════════════════════════════

    override suspend fun saveSkills(skills: List<String>) {
        val userId   = tokenManager.getUserId() ?: return
        val existing = userDao.getUserOnce(userId) ?: return

        // 1. Save to Room immediately (offline-first)
        val updated = existing.copy(
            skills    = skills,
            isSynced  = false,
            updatedAt = System.currentTimeMillis()
        )
        userDao.insertUser(updated)
        Log.d(TAG, "✅ Skills saved to Room: $skills")

        // 2. Try to push to backend immediately
        try {
            val response = authApiService.updateSkills(SkillsUpdateRequest(skills))
            if (response.isSuccessful) {
                userDao.updateSyncStatus(userId, synced = true)
                Log.d(TAG, "✅ Skills synced to backend")
            } else {
                Log.w(TAG, "⚠️ Skills sync failed (${response.code()}) — will retry via getUnsyncedUsers()")
            }
        } catch (e: Exception) {
            Log.w(TAG, "⚠️ Skills sync exception: ${e.message} — saved offline, will retry")
            // isSynced stays false — retry picks it up
        }
    }
    // ══════════════════════════════════════════════════════════
// SYNC UNSYNCED USERS (called by SyncWorker)
// ══════════════════════════════════════════════════════════

    suspend fun syncUnsyncedUsers() {
        val unsynced = userDao.getUnsyncedUsers()
        if (unsynced.isEmpty()) return

        Log.d(TAG, "🔄 Syncing ${unsynced.size} unsynced user(s)")

        for (user in unsynced) {
            try {
                val response = authApiService.updateSkills(SkillsUpdateRequest(user.skills))
                if (response.isSuccessful) {
                    userDao.updateSyncStatus(user.id, synced = true)
                    Log.d(TAG, "✅ Synced skills for user ${user.id}")
                } else {
                    Log.w(TAG, "⚠️ Failed to sync user ${user.id}: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.w(TAG, "⚠️ Sync exception for user ${user.id}: ${e.message}")
            }
        }
    }
}

// ══════════════════════════════════════════════════════════
// RemoteUser → UserEntity
// ══════════════════════════════════════════════════════════


