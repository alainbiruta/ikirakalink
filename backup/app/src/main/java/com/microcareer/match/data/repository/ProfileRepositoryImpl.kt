package com.microcareer.match.data.repository

import android.util.Log
import com.microcareer.match.data.local.TokenManager
import com.microcareer.match.data.local.dao.UserDao
import com.microcareer.match.data.local.entity.UserEntity
import com.microcareer.match.data.remote.api.AuthApiService
import com.microcareer.match.data.remote.models.SkillsUpdateRequest
import com.microcareer.match.domain.model.UserProfile
import com.microcareer.match.domain.repository.ProfileRepository

import com.microcareer.match.data.remote.models.UpdateProfileRequest
import com.microcareer.match.utils.NetworkMonitor
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ProfileRepository"

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val userDao       : UserDao,
    private val tokenManager  : TokenManager,
    private val authApiService: AuthApiService,   // ← ADD to constructor
    private val networkMonitor: NetworkMonitor    // ← ADD to constructor
) : ProfileRepository {

    // ══════════════════════════════════════════════════════
    // OBSERVE PROFILE
    // ══════════════════════════════════════════════════════

    override fun getUserProfile(): Flow<UserProfile> = flow {
        val userId = tokenManager.getUserId()

        if (userId == null) {
            emit(createDefaultProfile())
            return@flow
        }

        userDao.getUser(userId)
            .map { userEntity ->
                if (userEntity != null) {
                    userEntity.toUserProfile()
                } else {
                    // Race condition on first login — build from token
                    // until AuthRepository seeds the DB row
                    UserProfile(
                        id     = userId,
                        name   = tokenManager.getFullName()   ?: "User",
                        email  = tokenManager.getUserEmail()  ?: "",
                        skills = emptyList()
                    )
                }
            }
            .collect { emit(it) }
    }

    // ══════════════════════════════════════════════════════
    // SAVE SKILLS  — Room first, then backend
    // ══════════════════════════════════════════════════════

    override suspend fun saveSkills(skills: List<String>) {
        val userId = tokenManager.getUserId() ?: run {
            Log.w(TAG, "saveSkills: no userId in token — aborting")
            return
        }

        // 1. Write to Room immediately (optimistic, offline-safe)
        val currentUser = userDao.getUserOnce(userId)
        if (currentUser != null) {
            userDao.updateUser(
                currentUser.copy(
                    skills    = skills,
                    updatedAt = System.currentTimeMillis(),
                    isSynced  = false
                )
            )
        } else {
            // First-login race: seed the row so it exists for future updates
            userDao.insertUser(
                UserEntity(
                    id                = userId,
                    email             = tokenManager.getUserEmail() ?: "",
                    fullName          = tokenManager.getFullName()  ?: "User",
                    phoneNumber       = tokenManager.getPhoneNumber(),
                    profilePictureUrl = null,
                    location          = null,
                    latitude          = null,
                    longitude         = null,
                    role              = tokenManager.getUserRole(),
                    availableHours    = null,
                    companyName       = tokenManager.getCompanyName(),
                    rating            = 0f,
                    completedJobs     = 0,
                    skills            = skills,
                    createdAt         = System.currentTimeMillis(),
                    updatedAt         = System.currentTimeMillis(),
                    isSynced          = false
                )
            )
        }
        Log.d(TAG, "Skills written to Room: $skills")

        // 2. Sync to backend if online
        if (!networkMonitor.isCurrentlyOnline()) {
            Log.d(TAG, "Offline — skills queued, will sync when online")
            return
        }

        try {
            val response = authApiService.updateSkills(
                SkillsUpdateRequest(skills = skills)
            )
            if (response.isSuccessful) {
                // Mark as synced in Room
                userDao.getUserOnce(userId)?.let { user ->
                    userDao.updateUser(user.copy(isSynced = true))
                }
                Log.d(TAG, "✅ Skills synced to backend: $skills")
            } else {
                // Room already updated — skills are safe locally
                // Will re-sync next time user saves or app comes online
                Log.w(TAG, "Backend sync failed: ${response.code()} — kept locally")
            }
        } catch (e: Exception) {
            // Network error — Room is already updated, no data loss
            Log.w(TAG, "Backend sync exception: ${e.message} — kept locally")
        }
    }

    // ══════════════════════════════════════════════════════
    // LOGOUT
    // ══════════════════════════════════════════════════════

    override suspend fun logout() {
        try {
            tokenManager.clearAuth()
            userDao.clearAll()
            Log.d(TAG, "User logged out successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error during logout", e)
            throw e
        }
    }

    // ══════════════════════════════════════════════════════
    // PRIVATE HELPERS
    // ══════════════════════════════════════════════════════

    private fun createDefaultProfile() = UserProfile(
        id = "", name = "Guest", email = "", skills = emptyList()
    )
 override suspend fun savePersonalInfo(name: String, phone: String, location: String) {
        val userId  = tokenManager.getUserId() ?: return
        val current = userDao.getUserOnce(userId) ?: return

        userDao.updateUser(
            current.copy(
                fullName    = name.ifBlank     { current.fullName },
                phoneNumber = phone.ifBlank    { current.phoneNumber },
                location    = location.ifBlank { current.location },
                updatedAt   = System.currentTimeMillis(),
                isSynced    = false
            )
        )

        if (!networkMonitor.isCurrentlyOnline()) return

        try {
            val updated  = userDao.getUserOnce(userId) ?: return
            val response = authApiService.updateProfile(
                UpdateProfileRequest(
                    fullName    = updated.fullName,
                    phoneNumber = updated.phoneNumber,
                    location    = updated.location
                )
            )
            if (response.isSuccessful) {
                userDao.updateUser(updated.copy(isSynced = true))
                Log.d(TAG, "✅ Personal info synced to backend")
            }
        } catch (e: Exception) {
            Log.w(TAG, "Personal info sync exception: ${e.message} — kept locally")
        }
    }

    override suspend fun saveBio(bio: String) {
        val userId  = tokenManager.getUserId() ?: return
        val current = userDao.getUserOnce(userId) ?: return
        userDao.updateUser(
            current.copy(bio = bio, updatedAt = System.currentTimeMillis())
        )
        Log.d(TAG, "Bio saved to Room")
    }
}

// ── Entity → Domain mapper ─────────────────────────────────

private fun UserEntity.toUserProfile() = UserProfile(
    id              = id,
    name            = fullName,
    email           = email,
    skills          = skills,
    phoneNumber     = phoneNumber,
    location        = location,
    profilePhotoUrl = profilePictureUrl,
    rating          = rating,
    completedJobs   = completedJobs,
    bio             = bio
)