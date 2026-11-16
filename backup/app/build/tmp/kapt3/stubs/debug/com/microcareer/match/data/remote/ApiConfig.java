package com.microcareer.match.data.remote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import com.microcareer.match.BuildConfig;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import android.util.Log;

/**
 * Sealed Result class used by all repositories.
 *
 * Every Flow from a repository emits:
 *  Result.Loading  → show spinner
 *  Result.Success  → show data (isFromCache = true means stale, show subtle indicator)
 *  Result.Error    → show error message
 *
 * Usage in ViewModel:
 *
 *  viewModelScope.launch {
 *      authRepository.login(email, password).collect { result ->
 *          when (result) {
 *              is Result.Loading -> uiState = uiState.copy(isLoading = true)
 *              is Result.Success -> {
 *                  uiState = uiState.copy(isLoading = false, user = result.data)
 *                  navigate to home
 *              }
 *              is Result.Error -> {
 *                  uiState = uiState.copy(isLoading = false, error = result.message)
 *              }
 *          }
 *      }
 *  }
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000eX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000eX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/microcareer/match/data/remote/ApiConfig;", "", "()V", "API_VERSION", "", "BASE_URL", "getBASE_URL", "()Ljava/lang/String;", "BASE_URL_EMULATOR", "getBASE_URL_EMULATOR", "BASE_URL_LOCAL", "getBASE_URL_LOCAL", "BASE_URL_PRODUCTION", "CONNECT_TIMEOUT", "", "CONTENT_TYPE_JSON", "HEADER_ACCEPT", "HEADER_AUTHORIZATION", "READ_TIMEOUT", "WRITE_TIMEOUT", "getBaseUrl", "isInternetAvailable", "", "context", "Landroid/content/Context;", "app_debug"})
public final class ApiConfig {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BASE_URL_PRODUCTION = "https://production-domain.com/";
    public static final long CONNECT_TIMEOUT = 30L;
    public static final long READ_TIMEOUT = 30L;
    public static final long WRITE_TIMEOUT = 30L;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HEADER_ACCEPT = "Accept";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HEADER_AUTHORIZATION = "Authorization";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CONTENT_TYPE_JSON = "application/json";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String API_VERSION = "api/v1";
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.data.remote.ApiConfig INSTANCE = null;
    
    private ApiConfig() {
        super();
    }
    
    private final java.lang.String getBASE_URL_EMULATOR() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBASE_URL() {
        return null;
    }
    
    private final java.lang.String getBASE_URL_LOCAL() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBaseUrl() {
        return null;
    }
    
    private final boolean isInternetAvailable(android.content.Context context) {
        return false;
    }
}