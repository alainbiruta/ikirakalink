package com.microcareer.match.data.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.microcareer.match.R
import com.microcareer.match.data.local.FcmNotification
import com.microcareer.match.data.local.FcmNotificationStore
import com.microcareer.match.data.local.TokenManager
import com.microcareer.match.data.remote.api.AuthApiService
import com.microcareer.match.data.remote.models.FcmTokenRequest
import com.microcareer.match.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MicroCareerMessagingService : FirebaseMessagingService() {

    @Inject lateinit var tokenManager: TokenManager
    @Inject lateinit var authApiService: AuthApiService          // Day 19: resolves the TODO
    @Inject lateinit var notificationStore: FcmNotificationStore // Day 19: persists for sheets

    // Lives for the duration of the service process; not tied to any ViewModel.
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    companion object {
        const val EXTRA_NOTIFICATION_TYPE = "notification_type"
        const val EXTRA_JOB_ID            = "job_id"
        const val EXTRA_MODULE_ID         = "module_id"
        const val EXTRA_APPLICATION_ID    = "application_id"

        // notification_type values — must match FastAPI NotificationType enum
        const val TYPE_JOB_MATCHED          = "job_matched"
        const val TYPE_APPLICATION_ACCEPTED = "application_accepted"
        const val TYPE_APPLICATION_REJECTED = "application_rejected"
        const val TYPE_PAYMENT              = "payment"
        const val TYPE_SKILL_GAP            = "skill_gap"
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Token lifecycle
    // ──────────────────────────────────────────────────────────────────────────

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        android.util.Log.d("FCM", "New token: ${token.take(20)}…")

        // Always persist locally — available for AuthRepository to send after login
        tokenManager.saveFcmToken(token)

        // If the user is already logged in, push to backend immediately.
        // This covers token refresh for active users (no re-login needed).
        val authHeader = tokenManager.getAuthorizationHeader()
        if (authHeader != null) {
            serviceScope.launch {
                try {
                    authApiService.updateFcmToken(FcmTokenRequest(fcmToken = token))
                    android.util.Log.d("FCM", "Token pushed to backend ✅")
                } catch (e: Exception) {
                    // Non-fatal: token is saved locally; AuthRepository will retry on next login.
                    android.util.Log.w("FCM", "Token push failed (will retry on login): ${e.message}")
                }
            }
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Message handling
    // ──────────────────────────────────────────────────────────────────────────

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val type          = message.data["type"]           ?: TYPE_JOB_MATCHED
        val jobId         = message.data["job_id"]
        val moduleId      = message.data["module_id"]
        val applicationId = message.data["application_id"]

        val title = message.data["title"]
            ?: message.notification?.title
            ?: "MicroCareer"
        val body = message.data["body"]
            ?: message.notification?.body
            ?: ""

        // 1. Show system notification
        showNotification(
            title         = title,
            body          = body,
            type          = type,
            jobId         = jobId,
            moduleId      = moduleId,
            applicationId = applicationId
        )

        // 2. Persist for the in-app notification sheets (Employee + Employer)
        notificationStore.add(
            FcmNotification(
                id            = System.currentTimeMillis().toString(),
                type          = type,
                title         = title,
                body          = body,
                jobId         = jobId,
                applicationId = applicationId,
                receivedAt    = System.currentTimeMillis(),
                isRead        = false
            )
        )
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Private helpers
    // ──────────────────────────────────────────────────────────────────────────

    private fun showNotification(
        title: String,
        body: String,
        type: String,
        jobId: String?,
        moduleId: String?,
        applicationId: String?
    ) {
        val channelId = channelIdFor(type)

        val tapIntent = Intent(this, MainActivity::class.java).apply {
            // FLAG_ACTIVITY_SINGLE_TOP so we land in onNewIntent if already running
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            putExtra(EXTRA_NOTIFICATION_TYPE, type)
            jobId?.let         { putExtra(EXTRA_JOB_ID,         it) }
            moduleId?.let      { putExtra(EXTRA_MODULE_ID,       it) }
            applicationId?.let { putExtra(EXTRA_APPLICATION_ID,  it) }
        }

        val requestCode = System.currentTimeMillis().toInt()

        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            tapIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(priorityFor(channelId))
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(requestCode, notification)
    }

    private fun channelIdFor(type: String): String = when (type) {
        TYPE_JOB_MATCHED                              -> "new_jobs"
        TYPE_APPLICATION_ACCEPTED,
        TYPE_APPLICATION_REJECTED                     -> "new_jobs"
        TYPE_PAYMENT                                  -> "payments"
        TYPE_SKILL_GAP                                -> "skill_gaps"
        else                                          -> "new_jobs"
    }

    private fun priorityFor(channelId: String): Int = when (channelId) {
        "payments", "new_jobs" -> NotificationCompat.PRIORITY_HIGH
        "skill_gaps"           -> NotificationCompat.PRIORITY_DEFAULT
        "sync"                 -> NotificationCompat.PRIORITY_LOW
        else                   -> NotificationCompat.PRIORITY_DEFAULT
    }
}