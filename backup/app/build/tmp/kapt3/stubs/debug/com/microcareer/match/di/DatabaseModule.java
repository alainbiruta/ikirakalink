package com.microcareer.match.di;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.room.Room;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microcareer.match.BuildConfig;
import com.microcareer.match.data.local.dao.*;
import com.microcareer.match.data.local.database.MicroCareerDatabase;
import com.microcareer.match.data.remote.api.MicroCareerApiService;
import com.microcareer.match.data.repository.AuthRepository;
import com.microcareer.match.data.local.dao.OfflinePendingActionDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/microcareer/match/di/DatabaseModule;", "", "()V", "provideDatabase", "Lcom/microcareer/match/data/local/database/MicroCareerDatabase;", "context", "Landroid/content/Context;", "provideJobDao", "Lcom/microcareer/match/data/local/dao/JobDao;", "database", "provideLearningModuleDao", "Lcom/microcareer/match/data/local/dao/LearningModuleDao;", "provideOfflinePendingActionDao", "Lcom/microcareer/match/data/local/dao/OfflinePendingActionDao;", "provideSkillProfileDao", "Lcom/microcareer/match/data/local/dao/SkillProfileDao;", "provideUserDao", "Lcom/microcareer/match/data/local/dao/UserDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.di.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.local.database.MicroCareerDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.local.dao.UserDao provideUserDao(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.database.MicroCareerDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.local.dao.JobDao provideJobDao(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.database.MicroCareerDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.local.dao.LearningModuleDao provideLearningModuleDao(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.database.MicroCareerDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.local.dao.SkillProfileDao provideSkillProfileDao(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.database.MicroCareerDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.local.dao.OfflinePendingActionDao provideOfflinePendingActionDao(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.database.MicroCareerDatabase database) {
        return null;
    }
}