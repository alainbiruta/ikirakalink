package com.microcareer.match.presentation.home

// ─────────────────────────────────────────────────────────────────────────────
// Day 19 — Wire FcmNotificationStore into the existing notification sheets.
//
// Both screens already have a ModalBottomSheet triggered by the bell icon.
// This file shows the pattern — adapt to your existing sheet structure.
// ─────────────────────────────────────────────────────────────────────────────

import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

import com.microcareer.match.data.local.FcmNotification

import java.text.SimpleDateFormat
import java.util.*
import com.microcareer.match.R

// ─────────────────────────────────────────────────────────────────────────────
// Step 1 — Inject FcmNotificationStore into your existing HomeViewModel
//          (or EmployerHomeViewModel). Add these two lines:
//
//   @Inject lateinit var notificationStore: FcmNotificationStore
//
//   val fcmNotifications: StateFlow<List<FcmNotification>> = notificationStore.notifications
//
// If using constructor injection (recommended), add it as a constructor parameter:
//
//   class HomeViewModel @Inject constructor(
//       ...,
//       private val notificationStore: FcmNotificationStore
//   ) : ViewModel() {
//       val fcmNotifications = notificationStore.notifications.stateIn(
//           scope = viewModelScope,
//           started = SharingStarted.WhileSubscribed(5_000),
//           initialValue = emptyList()
//       )
//       fun markNotificationsRead() = notificationStore.markAllRead()
//   }
// ─────────────────────────────────────────────────────────────────────────────

// ─────────────────────────────────────────────────────────────────────────────
// Step 2 — Inside your existing ModalBottomSheet content (the one opened by
//          the bell icon), add this composable call where you want FCM items:
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Drop this into your existing notification ModalBottomSheet content.
 * Sits above (or below) the sync status and pending applicant sections
 * that already exist in both screens.
 *
 * Usage:
 *   val fcmNotifications by viewModel.fcmNotifications.collectAsState()
 *   FcmNotificationSheetSection(
 *       notifications = fcmNotifications,
 *       onClearAll = { viewModel.markNotificationsRead() }
 *   )
 */
@Composable
fun FcmNotificationSheetSection(
    notifications: List<FcmNotification>,
    onClearAll: () -> Unit
) {
    if (notifications.isEmpty()) return   // nothing to show; existing empty state handles it

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text  = "Recent alerts",
                style = MaterialTheme.typography.titleSmall
            )
            TextButton(onClick = onClearAll) {
                Text(stringResource(R.string.mark_all_read), style = MaterialTheme.typography.labelSmall)
            }
        }

        LazyColumn(modifier = Modifier.heightIn(max = 320.dp)) {
            items(
                items = notifications.take(10),   // cap at 10 to keep sheet compact
                key   = { it.id }
            ) { notification ->
                FcmNotificationItem(notification)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
private fun FcmNotificationItem(notification: FcmNotification) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Unread indicator
        if (!notification.isRead) {
            Surface(
                shape  = MaterialTheme.shapes.extraSmall,
                color  = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(8.dp)
            ) {}
        } else {
            Spacer(modifier = Modifier.size(8.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text     = notification.title,
                style    = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text     = notification.body,
                style    = MaterialTheme.typography.bodySmall,
                color    = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text  = notification.receivedAt.toRelativeTime(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Converts epoch millis to a human-readable relative string.
private fun Long.toRelativeTime(): String {
    val diff = System.currentTimeMillis() - this
    return when {
        diff < 60_000        -> "now"
        diff < 3_600_000     -> "${diff / 60_000}m"
        diff < 86_400_000    -> "${diff / 3_600_000}h"
        else                 -> SimpleDateFormat("MMM d", Locale.getDefault()).format(Date(this))
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Badge count helper — use this wherever you already show unread badge on bell
//
//   val fcmNotifications by viewModel.fcmNotifications.collectAsState()
//   val unreadCount = fcmNotifications.count { !it.isRead }
//
//   BadgedBox(badge = {
//       if (unreadCount > 0) Badge { Text(unreadCount.toString()) }
//   }) {
//       Icon(Icons.Default.Notifications, contentDescription = stringResource(R.string.notifications))
//   }
// ─────────────────────────────────────────────────────────────────────────────