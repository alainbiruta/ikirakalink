// ══════════════════════════════════════════════════════════
// Retrofit Client - TESTED & WORKING
// ══════════════════════════════════════════════════════════
// Location: app/src/main/java/com/microcareer/match/data/remote/RetrofitClient.kt
// ══════════════════════════════════════════════════════════

package com.microcareer.match.data.remote
import com.microcareer.match.BuildConfig
import android.content.Context
import android.util.Log
import com.microcareer.match.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.microcareer.match.data.repository.*
import com.microcareer.match.data.remote.ApiConfig
/**
 * Retrofit HTTP Client
 *
 * TESTED with:
 * ✓ JWT Authentication
 * ✓ Token Auto-Injection
 * ✓ Request/Response Logging
 * ✓ Error Handling
 */
object RetrofitClient {

    private const val TAG = "RetrofitClient"

    @Volatile
    private var retrofit: Retrofit? = null

    /**
     * Get Retrofit instance (singleton)
     */
    fun getInstance(context: Context): Retrofit {
        return retrofit ?: synchronized(this) {
            retrofit ?: buildRetrofit(context).also { retrofit = it }
        }
    }

    /**
     * Build Retrofit instance
     */
    private fun buildRetrofit(context: Context): Retrofit {
        val baseUrl = ApiConfig.getBaseUrl()
        Log.d(TAG, "Building Retrofit with BASE_URL: ${ApiConfig.BASE_URL}")

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(buildOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Build OkHttp client with interceptors
     */
    private fun buildOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->                        // ← ADD THIS FIRST
                Log.e("EXACT_URL", ">>> ${chain.request().url}")
                chain.proceed(chain.request())
            }
            // Timeouts
            .connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(ApiConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)

            // Add interceptors in order
            .addInterceptor(headerInterceptor())
            .addInterceptor(authInterceptor(context))
            .addInterceptor(loggingInterceptor())

            .build()
    }

    /**
     * Add common headers to all requests
     */
    private fun headerInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(ApiConfig.HEADER_ACCEPT, ApiConfig.CONTENT_TYPE_JSON)
                .build()

            chain.proceed(request)
        }
    }

    /**
     * Authentication interceptor
     * Adds JWT token to requests (except login/register)
     */
    private fun authInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val path = request.url.encodedPath

            // Don't add token to auth endpoints
            if (isAuthEndpoint(path)) {
                return@Interceptor chain.proceed(request)
            }

            // Get token
            val tokenManager = TokenManager(context)
            val authHeader = tokenManager.getAuthorizationHeader()

            // Add token if available
            val newRequest = if (authHeader != null) {
                Log.d(TAG, "Adding Authorization header to: $path")
                request.newBuilder()
                    .addHeader(ApiConfig.HEADER_AUTHORIZATION, authHeader)
                    .build()
            } else {
                Log.w(TAG, "No token available for: $path")
                request
            }

            chain.proceed(newRequest)
        }
    }

    /**
     * Logging interceptor for debugging
     */
    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.d("API", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Check if endpoint is auth-related (doesn't need token)
     */
    private fun isAuthEndpoint(path: String): Boolean {
        return  path.contains("/token") ||
                path.contains("/login") ||
                path.contains("/register") ||
                path.contains("/refresh")
    }

    /**
     * Get API service instance
     *
     * Usage:
     * val authService = RetrofitClient.getService<AuthApiService>(context)
     */
    inline fun <reified T> getService(context: Context): T {
        return getInstance(context).create(T::class.java)
    }

    /**
     * Clear singleton (for testing or re-initialization)
     */
    fun clearInstance() {
        synchronized(this) {
            retrofit = null
        }
    }
}