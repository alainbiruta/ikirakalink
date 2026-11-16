package com.microcareer.match.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmNotificationStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tokenManager: TokenManager
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("fcm_notifications", Context.MODE_PRIVATE)

    private val gson     = Gson()
    private val listType = object : TypeToken<MutableList<FcmNotification>>() {}.type

    private fun notificationsKey() = "notifications_${tokenManager.getUserId() ?: "guest"}"
    private val MAX_STORED = 50

    private val _notifications = MutableStateFlow<List<FcmNotification>>(load())
    val notifications: StateFlow<List<FcmNotification>> = _notifications.asStateFlow()

    // ── Write ─────────────────────────────────────────────────────────────────

    fun add(notification: FcmNotification) {
        val current = load().toMutableList()

        // FIX: upsert by id — if a notification with this id already exists,
        // replace it in-place instead of prepending a duplicate.
        // This is what caused the LazyColumn crash:
        //   key "55b602fe..._ACCEPTED" appeared twice → IllegalArgumentException.
        val existingIndex = current.indexOfFirst { it.id == notification.id }
        if (existingIndex != -1) {
            // Already stored — update in place, don't add a second entry.
            // This handles the case where checkApplicationStatusNotifications()
            // runs multiple times across app restarts.
            current[existingIndex] = notification
        } else {
            current.add(0, notification)          // newest first
            if (current.size > MAX_STORED) {
                current.subList(MAX_STORED, current.size).clear()
            }
        }

        save(current)
        _notifications.value = current
    }

    fun markAllRead() {
        val updated = load().map { it.copy(isRead = true) }.toMutableList()
        save(updated)
        _notifications.value = updated
    }

    fun clear() {
        prefs.edit().remove(notificationsKey()).apply()
        _notifications.value = emptyList()
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    fun unreadCount(): Int = _notifications.value.count { !it.isRead }

    // ── Private ───────────────────────────────────────────────────────────────

    private fun load(): MutableList<FcmNotification> {
        val json = prefs.getString(notificationsKey(), null) ?: return mutableListOf()
        return try {
            gson.fromJson(json, listType) ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    private fun save(list: List<FcmNotification>) {
        prefs.edit().putString(notificationsKey(), gson.toJson(list)).apply()
    }
}

data class FcmNotification(
    val id            : String,
    val type          : String,
    val title         : String,
    val body          : String,
    val jobId         : String?  = null,
    val applicationId : String?  = null,
    val receivedAt    : Long,
    val isRead        : Boolean  = false
)