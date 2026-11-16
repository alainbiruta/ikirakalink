package com.microcareer.match.data.repository;

import android.util.Log;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.local.entity.UserEntity;
import com.microcareer.match.data.remote.api.AuthApiService;
import com.microcareer.match.data.remote.models.SkillsUpdateRequest;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nH\u0016J\u0010\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\nH\u0016J\u001c\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u000e\u0010\u0014\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/microcareer/match/data/repository/UserRepository;", "Lcom/microcareer/match/domain/repository/UserRepository;", "authApiService", "Lcom/microcareer/match/data/remote/api/AuthApiService;", "userDao", "Lcom/microcareer/match/data/local/dao/UserDao;", "tokenManager", "Lcom/microcareer/match/data/local/TokenManager;", "(Lcom/microcareer/match/data/remote/api/AuthApiService;Lcom/microcareer/match/data/local/dao/UserDao;Lcom/microcareer/match/data/local/TokenManager;)V", "getProfile", "Lkotlinx/coroutines/flow/Flow;", "Lcom/microcareer/match/data/repository/AppResult;", "Lcom/microcareer/match/data/local/entity/UserEntity;", "observeCurrentUser", "saveSkills", "", "skills", "", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncUnsyncedUsers", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class UserRepository implements com.microcareer.match.domain.repository.UserRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.remote.api.AuthApiService authApiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.UserDao userDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.TokenManager tokenManager = null;
    
    @javax.inject.Inject()
    public UserRepository(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.api.AuthApiService authApiService, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.UserDao userDao, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.TokenManager tokenManager) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.microcareer.match.data.repository.AppResult<com.microcareer.match.data.local.entity.UserEntity>> getProfile() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.microcareer.match.data.local.entity.UserEntity> observeCurrentUser() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveSkills(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncUnsyncedUsers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}