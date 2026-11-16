package com.microcareer.match.data.repository;

import com.microcareer.match.data.local.entity.LearningModuleEntity;
import com.microcareer.match.domain.model.Resource;
import kotlinx.coroutines.flow.Flow;

/**
 * Repository for learning module content.
 *
 * Conventions:
 * - Flow<Resource<T>> → observable streams (auto-updates from Room)
 * - suspend Resource<T> → one-shot operations (sync, update progress)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u0003H&J\u001a\u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u0003H&J\u001e\u0010\b\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00040\u00032\u0006\u0010\t\u001a\u00020\nH&J\"\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u00032\u0006\u0010\f\u001a\u00020\nH&J$\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000fH&J\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0004H\u00a6@\u00a2\u0006\u0002\u0010\u0012J\"\u0010\u0013\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u00032\u0006\u0010\u0014\u001a\u00020\nH&J$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u000fH\u00a6@\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u0018"}, d2 = {"Lcom/microcareer/match/data/repository/LearningRepository;", "", "getAllModules", "Lkotlinx/coroutines/flow/Flow;", "Lcom/microcareer/match/domain/model/Resource;", "", "Lcom/microcareer/match/data/local/entity/LearningModuleEntity;", "getCompletedModules", "getModule", "moduleId", "", "getModulesForSkill", "skillName", "getRecommendedModules", "limit", "", "refreshModules", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchModules", "query", "updateProgress", "progress", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface LearningRepository {
    
    /**
     * All learning modules ordered by recommendation score.
     * Used for main learning screen when no filters are active.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> getAllModules();
    
    /**
     * Modules for a specific skill.
     * Used when user taps "Learn X" from skill gap recommendation.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> getModulesForSkill(@org.jetbrains.annotations.NotNull()
    java.lang.String skillName);
    
    /**
     * Top recommended modules based on user's skill gaps.
     * Used for personalized learning feed.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> getRecommendedModules(int limit);
    
    /**
     * Modules user has completed.
     * Used for "My Learning" → "Completed" tab.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> getCompletedModules();
    
    /**
     * Single module by ID.
     * Used for module detail screen.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<com.microcareer.match.data.local.entity.LearningModuleEntity>> getModule(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId);
    
    /**
     * Full-text search across title, description, skill.
     * Used for search bar in learning screen.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.microcareer.match.domain.model.Resource<java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity>>> searchModules(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    /**
     * Records progress for a module (0-100%).
     * Automatically marks as completed when progress reaches 100.
     * Syncs to backend in background via WorkManager.
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, int progress, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.microcareer.match.domain.model.Resource<kotlin.Unit>> $completion);
    
    /**
     * Fetches latest modules from backend and upserts into Room.
     * The observable flows above will emit automatically after sync.
     * Called by:
     * - Pull-to-refresh in learning screen
     * - Periodic background sync (WorkManager)
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object refreshModules(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.microcareer.match.domain.model.Resource<kotlin.Unit>> $completion);
    
    /**
     * Repository for learning module content.
     *
     * Conventions:
     * - Flow<Resource<T>> → observable streams (auto-updates from Room)
     * - suspend Resource<T> → one-shot operations (sync, update progress)
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}