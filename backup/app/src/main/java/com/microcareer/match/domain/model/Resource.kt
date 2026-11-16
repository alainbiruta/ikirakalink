package com.microcareer.match.domain.model

/**
 * Generic wrapper for UI-layer data states.
 *
 * Used by LearningRepository (and can be adopted by other
 * repositories over time as a replacement for AppResult).
 *
 * Loading  — operation in progress, optional stale data attached
 * Success  — data is ready
 * Error    — operation failed, optional stale data attached
 */
sealed class Resource<out T> {

    data class Loading<out T>(val data: T? = null) : Resource<T>()

    data class Success<out T>(val data: T) : Resource<T>()

    data class Error<out T>(
        val message: String,
        val data: T? = null
    ) : Resource<T>()
}