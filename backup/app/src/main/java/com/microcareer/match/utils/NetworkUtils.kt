package com.microcareer.match.utils

import retrofit2.HttpException
import java.io.IOException

fun networkErrorMessage(e: Exception): String {
    return when (e) {

        // 🔌 No internet / timeout / DNS issues
        is IOException -> "No internet connection. Please check your network."

        // 🌐 Server responded with error (4xx / 5xx)
        is HttpException -> {
            when (e.code()) {
                401 -> "Unauthorized access. Please login again."
                403 -> "Access forbidden."
                404 -> "Resource not found."
                500 -> "Server error. Try again later."
                else -> "Server error (${e.code()})"
            }
        }

        // ❓ Anything else
        else -> e.localizedMessage ?: "Unexpected error occurred"
    }
}
