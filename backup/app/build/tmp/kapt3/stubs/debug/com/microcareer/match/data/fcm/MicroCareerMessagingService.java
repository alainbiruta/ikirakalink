package com.microcareer.match.data.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.microcareer.match.R;
import com.microcareer.match.data.local.FcmNotification;
import com.microcareer.match.data.local.FcmNotificationStore;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.remote.api.AuthApiService;
import com.microcareer.match.data.remote.models.FcmTokenRequest;
import com.microcareer.match.presentation.MainActivity;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\b\u0007\u0018\u0000 )2\u00020\u0001:\u0001)B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u0018H\u0016J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0018H\u0002J>\u0010#\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u00182\u0006\u0010%\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\b\u0010&\u001a\u0004\u0018\u00010\u00182\b\u0010\'\u001a\u0004\u0018\u00010\u00182\b\u0010(\u001a\u0004\u0018\u00010\u0018H\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u00020\u00128\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006*"}, d2 = {"Lcom/microcareer/match/data/fcm/MicroCareerMessagingService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "authApiService", "Lcom/microcareer/match/data/remote/api/AuthApiService;", "getAuthApiService", "()Lcom/microcareer/match/data/remote/api/AuthApiService;", "setAuthApiService", "(Lcom/microcareer/match/data/remote/api/AuthApiService;)V", "notificationStore", "Lcom/microcareer/match/data/local/FcmNotificationStore;", "getNotificationStore", "()Lcom/microcareer/match/data/local/FcmNotificationStore;", "setNotificationStore", "(Lcom/microcareer/match/data/local/FcmNotificationStore;)V", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "tokenManager", "Lcom/microcareer/match/data/local/TokenManager;", "getTokenManager", "()Lcom/microcareer/match/data/local/TokenManager;", "setTokenManager", "(Lcom/microcareer/match/data/local/TokenManager;)V", "channelIdFor", "", "type", "onMessageReceived", "", "message", "Lcom/google/firebase/messaging/RemoteMessage;", "onNewToken", "token", "priorityFor", "", "channelId", "showNotification", "title", "body", "jobId", "moduleId", "applicationId", "Companion", "app_debug"})
public final class MicroCareerMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @javax.inject.Inject()
    public com.microcareer.match.data.local.TokenManager tokenManager;
    @javax.inject.Inject()
    public com.microcareer.match.data.remote.api.AuthApiService authApiService;
    @javax.inject.Inject()
    public com.microcareer.match.data.local.FcmNotificationStore notificationStore;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_NOTIFICATION_TYPE = "notification_type";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_JOB_ID = "job_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_MODULE_ID = "module_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_APPLICATION_ID = "application_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_JOB_MATCHED = "job_matched";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_APPLICATION_ACCEPTED = "application_accepted";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_APPLICATION_REJECTED = "application_rejected";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_PAYMENT = "payment";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TYPE_SKILL_GAP = "skill_gap";
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.data.fcm.MicroCareerMessagingService.Companion Companion = null;
    
    public MicroCareerMessagingService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.local.TokenManager getTokenManager() {
        return null;
    }
    
    public final void setTokenManager(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.TokenManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.remote.api.AuthApiService getAuthApiService() {
        return null;
    }
    
    public final void setAuthApiService(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.api.AuthApiService p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.local.FcmNotificationStore getNotificationStore() {
        return null;
    }
    
    public final void setNotificationStore(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.FcmNotificationStore p0) {
    }
    
    @java.lang.Override()
    public void onNewToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    @java.lang.Override()
    public void onMessageReceived(@org.jetbrains.annotations.NotNull()
    com.google.firebase.messaging.RemoteMessage message) {
    }
    
    private final void showNotification(java.lang.String title, java.lang.String body, java.lang.String type, java.lang.String jobId, java.lang.String moduleId, java.lang.String applicationId) {
    }
    
    private final java.lang.String channelIdFor(java.lang.String type) {
        return null;
    }
    
    private final int priorityFor(java.lang.String channelId) {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/microcareer/match/data/fcm/MicroCareerMessagingService$Companion;", "", "()V", "EXTRA_APPLICATION_ID", "", "EXTRA_JOB_ID", "EXTRA_MODULE_ID", "EXTRA_NOTIFICATION_TYPE", "TYPE_APPLICATION_ACCEPTED", "TYPE_APPLICATION_REJECTED", "TYPE_JOB_MATCHED", "TYPE_PAYMENT", "TYPE_SKILL_GAP", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}