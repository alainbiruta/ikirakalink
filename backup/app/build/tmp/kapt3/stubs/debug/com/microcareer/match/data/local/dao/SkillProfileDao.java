package com.microcareer.match.data.local.dao;

import androidx.room.*;
import com.microcareer.match.data.local.entity.SkillProfileEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\u00112\u0006\u0010\t\u001a\u00020\nH\'J\u0016\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u00020\u00032\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001e\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00a2\u0006\u0002\u0010\u001c\u00a8\u0006\u001d"}, d2 = {"Lcom/microcareer/match/data/local/dao/SkillProfileDao;", "", "deleteSkill", "", "skillId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSkill", "Lcom/microcareer/match/data/local/entity/SkillProfileEntity;", "userId", "", "skillName", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUnsyncedSkills", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserSkills", "Lkotlinx/coroutines/flow/Flow;", "insertSkill", "skill", "(Lcom/microcareer/match/data/local/entity/SkillProfileEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSkills", "skills", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSkill", "updateSyncStatus", "synced", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface SkillProfileDao {
    
    @androidx.room.Query(value = "SELECT * FROM skill_profiles WHERE userId = :userId ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.microcareer.match.data.local.entity.SkillProfileEntity>> getUserSkills(@org.jetbrains.annotations.NotNull()
    java.lang.String userId);
    
    @androidx.room.Query(value = "SELECT * FROM skill_profiles WHERE userId = :userId AND skillName = :skillName")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSkill(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String skillName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.microcareer.match.data.local.entity.SkillProfileEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSkill(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.entity.SkillProfileEntity skill, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSkills(@org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.data.local.entity.SkillProfileEntity> skills, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSkill(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.entity.SkillProfileEntity skill, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM skill_profiles WHERE id = :skillId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSkill(long skillId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM skill_profiles WHERE isSynced = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedSkills(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.microcareer.match.data.local.entity.SkillProfileEntity>> $completion);
    
    @androidx.room.Query(value = "UPDATE skill_profiles SET isSynced = :synced WHERE id = :skillId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSyncStatus(long skillId, boolean synced, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}