package com.microcareer.match.di;

import com.microcareer.match.data.repository.LearningRepositoryImpl;
import com.microcareer.match.data.repository.ProfileRepositoryImpl;
import com.microcareer.match.data.repository.UserRepository;
import com.microcareer.match.data.repository.LearningRepository;
import com.microcareer.match.domain.repository.ProfileRepository;
import com.microcareer.match.data.remote.api.LearningApiService;
import retrofit2.Retrofit;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\tH\'J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\fH\'\u00a8\u0006\u000e"}, d2 = {"Lcom/microcareer/match/di/RepositoryModule;", "", "()V", "bindLearningRepository", "Lcom/microcareer/match/data/repository/LearningRepository;", "impl", "Lcom/microcareer/match/data/repository/LearningRepositoryImpl;", "bindProfileRepository", "Lcom/microcareer/match/domain/repository/ProfileRepository;", "Lcom/microcareer/match/data/repository/ProfileRepositoryImpl;", "bindUserRepository", "Lcom/microcareer/match/domain/repository/UserRepository;", "Lcom/microcareer/match/data/repository/UserRepository;", "Companion", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.di.RepositoryModule.Companion Companion = null;
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.microcareer.match.domain.repository.ProfileRepository bindProfileRepository(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.repository.ProfileRepositoryImpl impl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.microcareer.match.data.repository.LearningRepository bindLearningRepository(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.repository.LearningRepositoryImpl impl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.microcareer.match.domain.repository.UserRepository bindUserRepository(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.repository.UserRepository impl);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Lcom/microcareer/match/di/RepositoryModule$Companion;", "", "()V", "provideLearningApiService", "Lcom/microcareer/match/data/remote/api/LearningApiService;", "retrofit", "Lretrofit2/Retrofit;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @dagger.Provides()
        @javax.inject.Singleton()
        @org.jetbrains.annotations.NotNull()
        public final com.microcareer.match.data.remote.api.LearningApiService provideLearningApiService(@org.jetbrains.annotations.NotNull()
        retrofit2.Retrofit retrofit) {
            return null;
        }
    }
}