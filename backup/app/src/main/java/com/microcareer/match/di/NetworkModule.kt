package com.microcareer.match.di

import android.content.Context
import com.microcareer.match.data.local.TokenManager
import com.microcareer.match.data.remote.ApiConfig
import com.microcareer.match.data.remote.api.ApplicationsApiService
import com.microcareer.match.data.remote.api.AuthApiService
import com.microcareer.match.data.remote.api.JobsApiService
import com.microcareer.match.data.remote.api.MicroCareerApiService
import com.microcareer.match.utils.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor = NetworkMonitor(context)

    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context
    ): TokenManager = TokenManager(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenManager: TokenManager): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request()
                val path = request.url.encodedPath
                val isAuthEndpoint = path.contains("/token") ||
                        path.contains("/login") ||
                        path.contains("/register") ||
                        path.contains("/refresh")

                if (isAuthEndpoint) return@addInterceptor chain.proceed(request)

                val authHeader = tokenManager.getAuthorizationHeader()
                android.util.Log.e("AUTH_DEBUG", "authHeader = $authHeader")
                val newRequest = if (authHeader != null) {
                    request.newBuilder()
                        .addHeader("Authorization", authHeader)
                        .build()
                } else request

                // ── Execute the request ──────────────────────────────
                val response = chain.proceed(newRequest)

                // ── On 401: token is expired/invalid ────────────────
                // DO NOT logout here. Just return the response so the
                // repository can handle it gracefully (show error, retry,
                // etc.) without triggering a forced logout.
                if (response.code == 401) {
                    android.util.Log.w("NetworkModule",
                        "401 on ${request.url} — token may be expired. " +
                                "User stays logged in; screen will show error.")
                    // Close the body to avoid leaks, then return it
                    // so callers can inspect the code if needed
                    return@addInterceptor response
                }

                response
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConfig.getBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideJobsApiService(retrofit: Retrofit): JobsApiService =
        retrofit.create(JobsApiService::class.java)

    @Provides
    @Singleton
    fun provideApplicationsApiService(retrofit: Retrofit): ApplicationsApiService =
        retrofit.create(ApplicationsApiService::class.java)

    @Provides
    @Singleton
    fun provideMicroCareerApiService(retrofit: Retrofit): MicroCareerApiService =
        retrofit.create(MicroCareerApiService::class.java)
}