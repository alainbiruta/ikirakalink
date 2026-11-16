package com.microcareer.match.domain.repository;

import com.microcareer.match.data.local.entity.UserEntity;
import com.microcareer.match.data.repository.AppResult;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H&J\u0010\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003H&J\u001c\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00a6@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/microcareer/match/domain/repository/UserRepository;", "", "getProfile", "Lkotlinx/coroutines/flow/Flow;", "Lcom/microcareer/match/data/repository/AppResult;", "Lcom/microcareer/match/data/local/entity/UserEntity;", "observeCurrentUser", "saveSkills", "", "skills", "", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface UserRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.microcareer.match.data.repository.AppResult<com.microcareer.match.data.local.entity.UserEntity>> getProfile();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.microcareer.match.data.local.entity.UserEntity> observeCurrentUser();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveSkills(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}