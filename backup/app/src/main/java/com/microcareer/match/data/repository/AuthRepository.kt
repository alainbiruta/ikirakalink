package com.microcareer.match.data.repository

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.microcareer.match.data.local.TokenManager
import com.microcareer.match.data.local.dao.UserDao
import com.microcareer.match.data.local.entity.UserEntity
import com.microcareer.match.data.remote.api.AuthApiService
import com.microcareer.match.data.remote.models.FcmTokenRequest
import com.microcareer.match.data.remote.models.RegisterRequest
import com.microcareer.match.data.remote.models.RemoteUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import com.microcareer.match.utils.networkErrorMessage

private const val TAG = "AuthRepository"

@Singleton
class AuthRepository @Inject constructor(
    private val authApiService : AuthApiService,
    private val userDao        : UserDao,
    private val tokenManager   : TokenManager,
    private val jobDao              : com.microcareer.match.data.local.dao.JobDao,
    private val fcmNotificationStore: com.microcareer.match.data.local.FcmNotificationStore
) {

    fun login(email: String, password: String): Flow<AppResult<UserEntity>> = flow {
        emit(AppResult.Loading)
        try {
            val response = authApiService.login(username = email, password = password)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                tokenManager.saveAccessToken(body.accessToken, body.tokenType)
                tokenManager.saveUserInfo(
                    userId      = body.user.id,
                    fullName    = body.user.fullName,
                    email       = body.user.email,
                    role        = body.user.role,
                    phoneNumber = body.user.phoneNumber,
                    companyName = body.user.companyName
                )
                pushFcmTokenIfAvailable()
                // FIX: preserve locally-saved skills on every login
                val existing = userDao.getUserOnce(body.user.id)
                val entity   = body.user.toEntityPreservingSkills(existing)
                userDao.insertUser(entity)
                Log.d(TAG, "Login success: ${body.user.email}, skills: ${entity.skills}")
                registerFcmToken()
                emit(AppResult.Success(entity))
            } else {
                val msg = when (response.code()) {
                    401  -> "Incorrect email or password"
                    422  -> "Invalid request format"
                    else -> "Login failed (${response.code()})"
                }
                emit(AppResult.Error(msg, response.code()))
            }
        } catch (e: Exception) {
            emit(AppResult.Error(networkErrorMessage(e)))
        }
    }

    fun register(
        email       : String,
        password    : String,
        fullName    : String,
        role        : String,
        phoneNumber : String? = null,
        location    : String? = null,
        companyName : String? = null
    ): Flow<AppResult<UserEntity>> = flow {
        emit(AppResult.Loading)
        try {
            val request = RegisterRequest(
                email       = email,
                password    = password,
                fullName    = fullName,
                role        = role,
                phoneNumber = phoneNumber,
                location    = location,
                companyName = companyName
            )
            pushFcmTokenIfAvailable()
            val response = authApiService.register(request)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                tokenManager.saveAccessToken(body.accessToken, body.tokenType)
                tokenManager.saveUserInfo(
                    userId      = body.user.id,
                    fullName    = body.user.fullName,
                    email       = body.user.email,
                    role        = body.user.role,
                    phoneNumber = body.user.phoneNumber,
                    companyName = body.user.companyName
                )
                val existing = userDao.getUserOnce(body.user.id)
                val entity   = body.user.toEntityPreservingSkills(existing)
                userDao.insertUser(entity)
                Log.d(TAG, "Register success: $email")
                registerFcmToken()
                emit(AppResult.Success(entity))
            } else {
                val msg = when (response.code()) {
                    400  -> "Email already registered"
                    422  -> "Please check all required fields"
                    else -> "Registration failed (${response.code()})"
                }
                emit(AppResult.Error(msg, response.code()))
            }
        } catch (e: Exception) {
            emit(AppResult.Error(networkErrorMessage(e)))
        }
    }

    suspend fun logout() {
        tokenManager.getUserId()?.let { userDao.deleteUser(it) }
        tokenManager.clearAuth()
        jobDao.clearAllJobs()
        fcmNotificationStore.clear()
        Log.d(TAG, "Logged out")
    }

    fun isLoggedIn(): Boolean = tokenManager.isLoggedIn()

    fun getCurrentUser(): Flow<UserEntity?> {
        val userId = tokenManager.getUserId() ?: return flow { emit(null) }
        return userDao.getUser(userId)
    }

    fun refreshCurrentUser(): Flow<AppResult<UserEntity>> = flow {
        emit(AppResult.Loading)
        val userId = tokenManager.getUserId()
        if (userId != null) {
            val cached = userDao.getUserOnce(userId)
            if (cached != null) emit(AppResult.Success(cached, isFromCache = true))
        }
        try {
            val response = authApiService.getCurrentUser()
            if (response.isSuccessful && response.body() != null) {
                val remoteUser = response.body()!!
                // FIX: preserve local skills on refresh — server may lag
                val existing = userId?.let { userDao.getUserOnce(it) }
                val entity   = remoteUser.toEntityPreservingSkills(existing)
                userDao.insertUser(entity)
                emit(AppResult.Success(entity))
            } else {
                if (userId == null) emit(AppResult.Error("Could not load profile"))
            }
        } catch (e: Exception) {
            if (userId == null) emit(AppResult.Error(networkErrorMessage(e)))
        }
    }

    private suspend fun registerFcmToken() {
        try {
            val fcmToken = FirebaseMessaging.getInstance().token.await()
            tokenManager.saveFcmToken(fcmToken)
            val response = authApiService.updateFcmToken(FcmTokenRequest(fcmToken))
            if (response.isSuccessful) Log.d(TAG, "FCM token registered")
            else Log.w(TAG, "FCM token upload failed: ${response.code()}")
        } catch (e: Exception) {
            Log.w(TAG, "FCM token registration skipped: ${e.message}")
        }
    }

    private suspend fun pushFcmTokenIfAvailable() {
        val fcmToken = tokenManager.getFcmToken() ?: return
        try {
            authApiService.updateFcmToken(FcmTokenRequest(fcmToken = fcmToken))
        } catch (e: Exception) {
            Log.w(TAG, "FCM token push failed: ${e.message}")
        }
    }
}

// ══════════════════════════════════════════════════════════
// RemoteUser → UserEntity
// ══════════════════════════════════════════════════════════

/**
 * Skill resolution priority:
 * 1. Server skills if non-empty (synced from another device)
 * 2. Local Room skills if non-empty (saved offline, server lagging)
 * 3. Empty list (brand new user)
 */
fun RemoteUser.toEntityPreservingSkills(existing: UserEntity?): UserEntity {
    val serverSkills  = this.skills ?: emptyList()
    val localSkills   = existing?.skills ?: emptyList()
    val resolvedSkills = when {
        serverSkills.isNotEmpty() -> serverSkills
        localSkills.isNotEmpty()  -> localSkills
        else                      -> emptyList()
    }
    return UserEntity(
        id                = this.id,
        email             = this.email,
        fullName          = this.fullName,
        role              = this.role,
        phoneNumber       = this.phoneNumber,
        profilePictureUrl = this.profilePictureUrl,
        location          = this.location,
        skills            = resolvedSkills,
        availableHours    = this.availableHours,
        companyName       = this.companyName,
        rating            = existing?.rating        ?: 0f,
        completedJobs     = existing?.completedJobs ?: 0,
        isSynced          = true
    )
}

// Keep original for test fixtures / places that want a clean entity
fun RemoteUser.toEntity(): UserEntity = toEntityPreservingSkills(null)