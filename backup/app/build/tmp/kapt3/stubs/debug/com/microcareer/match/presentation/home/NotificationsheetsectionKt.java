package com.microcareer.match.presentation.home;

import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextOverflow;
import com.microcareer.match.data.local.FcmNotification;
import java.text.SimpleDateFormat;
import java.util.*;
import com.microcareer.match.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\t\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a$\u0010\u0004\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a\f\u0010\t\u001a\u00020\n*\u00020\u000bH\u0002\u00a8\u0006\f"}, d2 = {"FcmNotificationItem", "", "notification", "Lcom/microcareer/match/data/local/FcmNotification;", "FcmNotificationSheetSection", "notifications", "", "onClearAll", "Lkotlin/Function0;", "toRelativeTime", "", "", "app_debug"})
public final class NotificationsheetsectionKt {
    
    /**
     * Drop this into your existing notification ModalBottomSheet content.
     * Sits above (or below) the sync status and pending applicant sections
     * that already exist in both screens.
     *
     * Usage:
     *  val fcmNotifications by viewModel.fcmNotifications.collectAsState()
     *  FcmNotificationSheetSection(
     *      notifications = fcmNotifications,
     *      onClearAll = { viewModel.markNotificationsRead() }
     *  )
     */
    @androidx.compose.runtime.Composable()
    public static final void FcmNotificationSheetSection(@org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.FcmNotification> notifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClearAll) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FcmNotificationItem(com.microcareer.match.data.local.FcmNotification notification) {
    }
    
    private static final java.lang.String toRelativeTime(long $this$toRelativeTime) {
        return null;
    }
}