package com.microcareer.match.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.ComponentActivity;
import androidx.compose.runtime.State;
import androidx.compose.ui.Modifier;
import com.microcareer.match.data.fcm.MicroCareerMessagingService;
import com.microcareer.match.data.sync.SyncManager;
import com.microcareer.match.presentation.navigation.NotificationDestination;
import com.microcareer.match.utils.LocaleHelper;
import dagger.hilt.android.AndroidEntryPoint;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J\u0006\u0010\u0010\u001a\u00020\rJ\u0012\u0010\u0011\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0002J\u0012\u0010\u0014\u001a\u00020\r2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\b\u0010\u0017\u001a\u00020\rH\u0014J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0014J\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/microcareer/match/presentation/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "_notificationDestination", "Landroidx/compose/runtime/MutableState;", "", "notificationDestination", "Landroidx/compose/runtime/State;", "getNotificationDestination", "()Landroidx/compose/runtime/State;", "syncManager", "Lcom/microcareer/match/data/sync/SyncManager;", "attachBaseContext", "", "base", "Landroid/content/Context;", "clearNotificationDestination", "handleNotificationIntent", "intent", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onNewIntent", "resolveNotificationDestination", "Lcom/microcareer/match/presentation/navigation/NotificationDestination;", "app_debug"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    private com.microcareer.match.data.sync.SyncManager syncManager;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState<java.lang.String> _notificationDestination = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.State<java.lang.String> notificationDestination = null;
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.State<java.lang.String> getNotificationDestination() {
        return null;
    }
    
    @java.lang.Override()
    protected void attachBaseContext(@org.jetbrains.annotations.NotNull()
    android.content.Context base) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onNewIntent(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    private final void handleNotificationIntent(android.content.Intent intent) {
    }
    
    private final com.microcareer.match.presentation.navigation.NotificationDestination resolveNotificationDestination(android.content.Intent intent) {
        return null;
    }
    
    public final void clearNotificationDestination() {
    }
}