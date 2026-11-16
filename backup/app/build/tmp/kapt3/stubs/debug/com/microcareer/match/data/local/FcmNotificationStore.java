package com.microcareer.match.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\fJ\u0006\u0010\u001b\u001a\u00020\u0019J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\f0\u001dH\u0002J\u0006\u0010\u001e\u001a\u00020\u0019J\b\u0010\u001f\u001a\u00020 H\u0002J\u0016\u0010!\u001a\u00020\u00192\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0006\u0010#\u001a\u00020\bR\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n \u0011*\u0004\u0018\u00010\u00100\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/microcareer/match/data/local/FcmNotificationStore;", "", "context", "Landroid/content/Context;", "tokenManager", "Lcom/microcareer/match/data/local/TokenManager;", "(Landroid/content/Context;Lcom/microcareer/match/data/local/TokenManager;)V", "MAX_STORED", "", "_notifications", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/microcareer/match/data/local/FcmNotification;", "gson", "Lcom/google/gson/Gson;", "listType", "Ljava/lang/reflect/Type;", "kotlin.jvm.PlatformType", "notifications", "Lkotlinx/coroutines/flow/StateFlow;", "getNotifications", "()Lkotlinx/coroutines/flow/StateFlow;", "prefs", "Landroid/content/SharedPreferences;", "add", "", "notification", "clear", "load", "", "markAllRead", "notificationsKey", "", "save", "list", "unreadCount", "app_debug"})
public final class FcmNotificationStore {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.TokenManager tokenManager = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    private final java.lang.reflect.Type listType = null;
    private final int MAX_STORED = 50;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.microcareer.match.data.local.FcmNotification>> _notifications = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.microcareer.match.data.local.FcmNotification>> notifications = null;
    
    @javax.inject.Inject()
    public FcmNotificationStore(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.TokenManager tokenManager) {
        super();
    }
    
    private final java.lang.String notificationsKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.microcareer.match.data.local.FcmNotification>> getNotifications() {
        return null;
    }
    
    public final void add(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.FcmNotification notification) {
    }
    
    public final void markAllRead() {
    }
    
    public final void clear() {
    }
    
    public final int unreadCount() {
        return 0;
    }
    
    private final java.util.List<com.microcareer.match.data.local.FcmNotification> load() {
        return null;
    }
    
    private final void save(java.util.List<com.microcareer.match.data.local.FcmNotification> list) {
    }
}