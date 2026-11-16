package com.microcareer.match.data.repository

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Sealed class for all repository emissions.
 * Named AppResult to avoid conflict with Kotlin stdlib Result.
 *
 * Usage in ViewModel:
 *   when (result) {
 *       is AppResult.Loading -> show spinner
 *       is AppResult.Success -> show data
 *       is AppResult.Error   -> show error
 *   }
 */
sealed class AppResult<out T> {
    object Loading : AppResult<Nothing>()

    data class Success<T>(
        val data: T,
        val isFromCache: Boolean = false
    ) : AppResult<T>()

    data class Error(
        val message: String,
        val code: Int? = null
    ) : AppResult<Nothing>()
}