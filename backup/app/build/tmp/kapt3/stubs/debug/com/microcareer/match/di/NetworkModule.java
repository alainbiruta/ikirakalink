package com.microcareer.match.di;

import android.content.Context;
import com.microcareer.match.data.local.TokenManager;
import com.microcareer.match.data.remote.ApiConfig;
import com.microcareer.match.data.remote.api.ApplicationsApiService;
import com.microcareer.match.data.remote.api.AuthApiService;
import com.microcareer.match.data.remote.api.JobsApiService;
import com.microcareer.match.data.remote.api.MicroCareerApiService;
import com.microcareer.match.utils.NetworkMonitor;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0012H\u0007J\u0012\u0010\u0017\u001a\u00020\u00142\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u0007\u00a8\u0006\u0018"}, d2 = {"Lcom/microcareer/match/di/NetworkModule;", "", "()V", "provideApplicationsApiService", "Lcom/microcareer/match/data/remote/api/ApplicationsApiService;", "retrofit", "Lretrofit2/Retrofit;", "provideAuthApiService", "Lcom/microcareer/match/data/remote/api/AuthApiService;", "provideJobsApiService", "Lcom/microcareer/match/data/remote/api/JobsApiService;", "provideMicroCareerApiService", "Lcom/microcareer/match/data/remote/api/MicroCareerApiService;", "provideNetworkMonitor", "Lcom/microcareer/match/utils/NetworkMonitor;", "context", "Landroid/content/Context;", "provideOkHttpClient", "Lokhttp3/OkHttpClient;", "tokenManager", "Lcom/microcareer/match/data/local/TokenManager;", "provideRetrofit", "okHttpClient", "provideTokenManager", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class NetworkModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.di.NetworkModule INSTANCE = null;
    
    private NetworkModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.utils.NetworkMonitor provideNetworkMonitor(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.local.TokenManager provideTokenManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient provideOkHttpClient(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.local.TokenManager tokenManager) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit provideRetrofit(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient okHttpClient) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.remote.api.AuthApiService provideAuthApiService(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.remote.api.JobsApiService provideJobsApiService(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.remote.api.ApplicationsApiService provideApplicationsApiService(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.data.remote.api.MicroCareerApiService provideMicroCareerApiService(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
}