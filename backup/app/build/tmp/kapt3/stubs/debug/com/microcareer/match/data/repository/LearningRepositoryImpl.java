package com.microcareer.match.data.repository;

import android.util.Log;
import com.microcareer.match.data.local.dao.LearningModuleDao;
import com.microcareer.match.data.local.entity.LearningModuleEntity;
import com.microcareer.match.data.remote.api.LearningApiService;
import com.microcareer.match.data.remote.dto.LearningModuleDto;
import com.microcareer.match.domain.model.Resource;
import com.microcareer.match.utils.NetworkMonitor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\"\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u000f\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010\u0013J\u001a\u0010\u0014\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\r0\f0\u0015H\u0016J\u001a\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\r0\f0\u0015H\u0016J\u001e\u0010\u0018\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\f0\u00152\u0006\u0010\u000f\u001a\u00020\u000eH\u0016J\"\u0010\u0019\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\r0\f0\u00152\u0006\u0010\u001a\u001a\u00020\u000eH\u0016J\"\u0010\u001b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\r0\f0\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\r2\b\u0010 \u001a\u0004\u0018\u00010!H\u0002J\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00120\fH\u0096@\u00a2\u0006\u0002\u0010\u0013J\"\u0010#\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\r0\f0\u00152\u0006\u0010$\u001a\u00020\u000eH\u0016J$\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00120\f2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010\'J*\u0010(\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\r0\f0\u0015*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\r0\u0015H\u0002J\"\u0010)\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\f0\u0015*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u0015H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/microcareer/match/data/repository/LearningRepositoryImpl;", "Lcom/microcareer/match/data/repository/LearningRepository;", "learningModuleDao", "Lcom/microcareer/match/data/local/dao/LearningModuleDao;", "learningApiService", "Lcom/microcareer/match/data/remote/api/LearningApiService;", "networkMonitor", "Lcom/microcareer/match/utils/NetworkMonitor;", "gson", "Lcom/google/gson/Gson;", "(Lcom/microcareer/match/data/local/dao/LearningModuleDao;Lcom/microcareer/match/data/remote/api/LearningApiService;Lcom/microcareer/match/utils/NetworkMonitor;Lcom/google/gson/Gson;)V", "completeModule", "Lcom/microcareer/match/domain/model/Resource;", "", "", "moduleId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchAndMergeRecommendations", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllModules", "Lkotlinx/coroutines/flow/Flow;", "Lcom/microcareer/match/data/local/entity/LearningModuleEntity;", "getCompletedModules", "getModule", "getModulesForSkill", "skillName", "getRecommendedModules", "limit", "", "parseModuleList", "Lcom/microcareer/match/data/remote/dto/LearningModuleDto;", "data", "", "refreshModules", "searchModules", "query", "updateProgress", "progress", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "asResource", "asSingleResource", "app_debug"})
public final class LearningRepositoryImpl implements com.microcareer.match.data.repository.LearningRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.LearningModuleDao learningModuleDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.remote.api.LearningApiService learningApiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.utils.NetworkMonitor networkMonitor = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    
    @javax.inject.Inject()
    public LearningRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.LearningModuleDao learningModuleDao, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.remote.api.LearningApiService learningApiService, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.utils.NetworkMonitor networkMonitor, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson) {
        super();
    }
    
    private final kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> asResource(kotlinx.coroutines.flow.Flow<? extends java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>> $this$asResource) {
        return null;
    }
    
    private final kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<com.microcareer.match.data.local.entity.LearningModuleEntity>> asSingleResource(kotlinx.coroutines.flow.Flow<com.microcareer.match.data.local.entity.LearningModuleEntity> $this$asSingleResource) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> getAllModules() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> getRecommendedModules(int limit) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> getCompletedModules() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<com.microcareer.match.data.local.entity.LearningModuleEntity>> getModule(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> getModulesForSkill(@org.jetbrains.annotations.NotNull()
    java.lang.String skillName) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> searchModules(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object refreshModules(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.microcareer.match.domain.model.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    /**
     * Pulls /recommendations/me and updates recommendationScore + progress in Room.
     */
    private final java.lang.Object fetchAndMergeRecommendations(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, int progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.microcareer.match.domain.model.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    /**
     * Marks the module complete locally AND calls the backend
     * POST /learning/{moduleId}/complete endpoint which adds
     * the module's skill_name to user.skills server-side.
     *
     * Returns the list of newly unlocked skill names so the
     * ViewModel can show the celebration dialog with exact text.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object completeModule(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.microcareer.match.domain.model.Resource<? extends java.util.List<java.lang.String>>> $completion) {
        return null;
    }
    
    /**
     * Safely parses the "modules" or "recommended" list from
     * the generic ApiResponse<Any>.data map.
     */
    @kotlin.Suppress(names = {"UNCHECKED_CAST"})
    private final java.util.List<com.microcareer.match.data.remote.dto.LearningModuleDto> parseModuleList(java.lang.Object data) {
        return null;
    }
}