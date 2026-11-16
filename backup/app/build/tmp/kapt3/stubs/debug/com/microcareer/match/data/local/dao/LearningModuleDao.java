package com.microcareer.match.data.local.dao;

import androidx.room.*;
import com.microcareer.match.data.local.entity.LearningModuleEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\'J\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\'J\u0018\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\b2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u001c\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u000e\u001a\u00020\u0005H\'J\u001e\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\'J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\u00020\u00032\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00a7@\u00a2\u0006\u0002\u0010\u0019J \u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u001b\u001a\u00020\u001cH\u00a7@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u001e\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010!J\u001e\u0010\"\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010#\u001a\u00020$H\u00a7@\u00a2\u0006\u0002\u0010%\u00a8\u0006&"}, d2 = {"Lcom/microcareer/match/data/local/dao/LearningModuleDao;", "", "deleteModule", "", "moduleId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllLearningModules", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/microcareer/match/data/local/entity/LearningModuleEntity;", "getCompletedModules", "getModule", "getModulesForSkill", "skillName", "getRecommendedModules", "limit", "", "getUnsyncedModules", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertModule", "module", "(Lcom/microcareer/match/data/local/entity/LearningModuleEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertModules", "modules", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsCompleted", "completedAt", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateModule", "updateProgress", "progress", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSyncStatus", "synced", "", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface LearningModuleDao {
    
    @androidx.room.Query(value = "SELECT * FROM learning_modules ORDER BY recommendationScore DESC, createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>> getAllLearningModules();
    
    @androidx.room.Query(value = "SELECT * FROM learning_modules WHERE isCompleted = 0 ORDER BY recommendationScore DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>> getRecommendedModules(int limit);
    
    @androidx.room.Query(value = "SELECT * FROM learning_modules WHERE id = :moduleId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.microcareer.match.data.local.entity.LearningModuleEntity> getModule(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId);
    
    @androidx.room.Query(value = "SELECT * FROM learning_modules WHERE skillName = :skillName")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>> getModulesForSkill(@org.jetbrains.annotations.NotNull()
    java.lang.String skillName);
    
    @androidx.room.Query(value = "SELECT * FROM learning_modules WHERE isCompleted = 1 ORDER BY completedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>> getCompletedModules();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertModule(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.entity.LearningModuleEntity module, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertModules(@org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity> modules, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateModule(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.entity.LearningModuleEntity module, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE learning_modules SET progress = :progress WHERE id = :moduleId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, int progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE learning_modules SET isCompleted = 1, completedAt = :completedAt WHERE id = :moduleId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markAsCompleted(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, long completedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE learning_modules SET isSynced = :synced WHERE id = :moduleId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSyncStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, boolean synced, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM learning_modules WHERE isSynced = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedModules(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>> $completion);
    
    @androidx.room.Query(value = "DELETE FROM learning_modules WHERE id = :moduleId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteModule(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}