package com.microcareer.match.data.repository;

import android.util.Log;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.local.entity.UserEntity;
import com.microcareer.match.data.remote.api.AuthApiService;
import com.microcareer.match.data.remote.models.SkillsUpdateRequest;
import com.microcareer.match.domain.model.UserProfile;
import com.microcareer.match.domain.repository.ProfileRepository;
import com.microcareer.match.data.remote.models.UpdateProfileRequest;
import com.microcareer.match.utils.NetworkMonitor;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0002J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000eH\u0016J\u000e\u0010\u000f\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0096@\u00a2\u0006\u0002\u0010\u0015J&\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0014H\u0096@\u00a2\u0006\u0002\u0010\u001aJ\u001c\u0010\u001b\u001a\u00020\u00102\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00140\u001dH\u0096@\u00a2\u0006\u0002\u0010\u001eR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/microcareer/match/data/repository/ProfileRepositoryImpl;", "Lcom/microcareer/match/domain/repository/ProfileRepository;", "userDao", "Lcom/microcareer/match/data/local/dao/UserDao;", "tokenManager", "Lcom/microcareer/match/data/local/TokenManager;", "authApiService", "Lcom/microcareer/match/data/remote/api/AuthApiService;", "networkMonitor", "Lcom/microcareer/match/utils/NetworkMonitor;", "(Lcom/microcareer/match/data/local/dao/UserDao;Lcom/microcareer/match/data/local/TokenManager;Lcom/microcareer/match/data/remote/api/AuthApiService;Lcom/microcareer/match/utils/NetworkMonitor;)V", "createDefaultProfile", "Lcom/microcareer/match/domain/model/UserProfile;", "getUserProfile", "Lkotlinx/coroutines/flow/Flow;", "logout", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveBio", "bio", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "savePersonalInfo", "name", "phone", "location", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveSkills", "skills", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ProfileRepositoryImpl implements com.microcareer.match.domain.repository.ProfileRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.UserDao userDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.TokenManager tokenManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.remote.api.AuthApiService authApiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.utils.NetworkMonitor networkMonitor = null;
    
    @javax.inject.Inject()
    public ProfileRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.UserDao userDao, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.TokenManager tokenManager, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.api.AuthApiService authApiService, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.utils.NetworkMonitor networkMonitor) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.UserProfile> getUserProfile() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveSkills(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.microcareer.match.domain.model.UserProfile createDefaultProfile() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object savePersonalInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String location, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveBio(@org.jetbrains.annotations.NotNull()
    java.lang.String bio, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}