package com.microcareer.match.data.local.seeder;

import com.microcareer.match.data.local.dao.JobDao;
import com.microcareer.match.data.local.dao.LearningModuleDao;
import com.microcareer.match.data.local.dao.UserDao;
import com.microcareer.match.data.local.entity.JobEntity;
import com.microcareer.match.data.local.entity.LearningModuleEntity;
import com.microcareer.match.data.local.entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlinx.coroutines.Dispatchers;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u0010\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u0011\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u0012\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010\rR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/microcareer/match/data/local/seeder/DatabaseSeeder;", "", "jobDao", "Lcom/microcareer/match/data/local/dao/JobDao;", "learningModuleDao", "Lcom/microcareer/match/data/local/dao/LearningModuleDao;", "userDao", "Lcom/microcareer/match/data/local/dao/UserDao;", "firebaseAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "(Lcom/microcareer/match/data/local/dao/JobDao;Lcom/microcareer/match/data/local/dao/LearningModuleDao;Lcom/microcareer/match/data/local/dao/UserDao;Lcom/google/firebase/auth/FirebaseAuth;)V", "clearAndReseed", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "seedCurrentUser", "", "seedDatabase", "seedJobs", "seedLearningModules", "app_debug"})
public final class DatabaseSeeder {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.JobDao jobDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.LearningModuleDao learningModuleDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.local.dao.UserDao userDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth firebaseAuth = null;
    
    @javax.inject.Inject()
    public DatabaseSeeder(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.JobDao jobDao, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.LearningModuleDao learningModuleDao, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.dao.UserDao userDao, @org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth firebaseAuth) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object seedDatabase(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearAndReseed(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    private final java.lang.Object seedJobs(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object seedLearningModules(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object seedCurrentUser(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}