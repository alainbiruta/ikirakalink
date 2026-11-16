package com.microcareer.match.data.repository;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessaging;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.local.entity.UserEntity;
import com.microcareer.match.data.remote.api.AuthApiService;
import com.microcareer.match.data.remote.models.FcmTokenRequest;
import com.microcareer.match.data.remote.models.RegisterRequest;
import com.microcareer.match.data.remote.models.RemoteUser;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000eJ\u0006\u0010\u0010\u001a\u00020\u0011J\"\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015J\u000e\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u0018H\u0082@\u00a2\u0006\u0002\u0010\u0019J\u0012\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\u000eJV\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00152\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u0015J\u000e\u0010\"\u001a\u00020\u0018H\u0082@\u00a2\u0006\u0002\u0010\u0019R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/microcareer/match/data/repository/AuthRepository;", "", "authApiService", "Lcom/microcareer/match/data/remote/api/AuthApiService;", "userDao", "Lcom/microcareer/match/data/local/dao/UserDao;", "tokenManager", "Lcom/microcareer/match/data/local/TokenManager;", "jobDao", "Lcom/microcareer/match/data/local/dao/JobDao;", "fcmNotificationStore", "Lcom/microcareer/match/data/local/FcmNotificationStore;", "(Lcom/microcareer/match/data/remote/api/AuthApiService;Lcom/microcareer/match/data/local/dao/UserDao;Lcom/microcareer/match/data/local/TokenManager;Lcom/microcareer/match/data/local/dao/JobDao;Lcom/microcareer/match/data/local/FcmNotificationStore;)V", "getCurrentUser", "Lkotlinx/coroutines/flow/Flow;", "Lcom/microcareer/match/data/local/entity/UserEntity;", "isLoggedIn", "", "login", "Lcom/microcareer/match/data/repository/AppResult;", "email", "", "password", "logout", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pushFcmTokenIfAvailable", "refreshCurrentUser", "register", "fullName", "role", "phoneNumber", "location", "companyName", "registerFcmToken", "app_debug"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.remote.api.AuthApiService authApiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.UserDao userDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.TokenManager tokenManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.JobDao jobDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.FcmNotificationStore fcmNotificationStore = null;
    
    @javax.inject.Inject()
    public AuthRepository(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.api.AuthApiService authApiService, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.UserDao userDao, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.TokenManager tokenManager, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.JobDao jobDao, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.FcmNotificationStore fcmNotificationStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.microcareer.match.data.repository.AppResult<com.microcareer.match.data.local.entity.UserEntity>> login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.microcareer.match.data.repository.AppResult<com.microcareer.match.data.local.entity.UserEntity>> register(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String role, @org.jetbrains.annotations.Nullable()
    java.lang.String phoneNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.Nullable()
    java.lang.String companyName) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.microcareer.match.data.local.entity.UserEntity> getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.microcareer.match.data.repository.AppResult<com.microcareer.match.data.local.entity.UserEntity>> refreshCurrentUser() {
        return null;
    }
    
    private final java.lang.Object registerFcmToken(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object pushFcmTokenIfAvailable(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}