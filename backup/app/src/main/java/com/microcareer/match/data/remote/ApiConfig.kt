package com.microcareer.match.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.microcareer.match.BuildConfig
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import android.util.Log
/**
 * Sealed Result class used by all repositories.
 *
 * Every Flow from a repository emits:
 *   Result.Loading  → show spinner
 *   Result.Success  → show data (isFromCache = true means stale, show subtle indicator)
 *   Result.Error    → show error message
 *
 * Usage in ViewModel:
 *
 *   viewModelScope.launch {
 *       authRepository.login(email, password).collect { result ->
 *           when (result) {
 *               is Result.Loading -> uiState = uiState.copy(isLoading = true)
 *               is Result.Success -> {
 *                   uiState = uiState.copy(isLoading = false, user = result.data)
 *                   navigate to home
 *               }
 *               is Result.Error -> {
 *                   uiState = uiState.copy(isLoading = false, error = result.message)
 *               }
 *           }
 *       }
 *   }
 */
object ApiConfig {

    // ── Base URLs ─────────────────────────────────────────────────
    private val BASE_URL_EMULATOR: String get() = BuildConfig.API_BASE_URL
    val BASE_URL: String get() = BuildConfig.API_BASE_URL
    private val BASE_URL_LOCAL: String get() = BuildConfig.LOCAL_BASE_URL
    private const val BASE_URL_PRODUCTION = "https://production-domain.com/"

    // ── Dynamic URL picker (NEW) ──────────────────────────────────
    // Use this in your Retrofit/OkHttp provider instead of BASE_URL
    // when you want automatic local/public switching.



    fun getBaseUrl(): String {
        return try {
            val socket = java.net.Socket()
            socket.connect(java.net.InetSocketAddress("10.0.2.2", 8000), 1500)
            socket.close()
            Log.d("ApiConfig", "✅ Using LOCAL: $BASE_URL_LOCAL")
            BASE_URL_LOCAL
        } catch (e: Exception) {
            Log.d("ApiConfig", "⚠️ Using PUBLIC: $BASE_URL (reason: ${e.message})")
            BASE_URL
        }
    }

    // ── Timeouts (seconds) ────────────────────────────────────────
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT    = 30L
    const val WRITE_TIMEOUT   = 30L

    // ── Headers ───────────────────────────────────────────────────
    const val HEADER_ACCEPT        = "Accept"
    const val HEADER_AUTHORIZATION = "Authorization"
    const val CONTENT_TYPE_JSON    = "application/json"

    // ── Misc ──────────────────────────────────────────────────────
    const val API_VERSION = "api/v1"

    // ── Internal connectivity check ───────────────────────────────
    private fun isInternetAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val caps = cm.getNetworkCapabilities(cm.activeNetwork ?: return false) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

sealed class Result<out T> {

    object Loading : Result<Nothing>()

    data class Success<T>(
        val data: T,
        val isFromCache: Boolean = false  // true = stale data from Room, fresh fetch still in flight
    ) : Result<T>()

    data class Error(
        val message: String,
        val code: Int? = null
    ) : Result<Nothing>()
}

/**
 * Converts network exceptions to human-readable messages.
 * Called by all repositories in their catch blocks.
 */
fun networkErrorMessage(e: Exception): String = when (e) {
    is UnknownHostException   -> "No internet connection"
    is SocketTimeoutException -> "Connection timed out — please try again"
    is IOException            -> "Network error — please check your connection"
    else                      -> e.message ?: "An unexpected error occurred"
}