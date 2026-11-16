package com.microcareer.match.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room TypeConverters for complex types.
 *
 * CRITICAL: Room expects non-null returns. Returning null can cause
 * crashes when Room tries to populate entities. Always return safe
 * defaults (empty collections) instead of null.
 *
 * Used by: JobEntity, UserEntity, LearningModuleEntity, SkillProfileEntity
 */
class ListConverters {

    private val gson = Gson()

    /* ── String List Converter ───────────────────────────────── */

    /**
     * Converts List<String> → JSON String for storage in SQLite.
     *
     * Examples:
     *   ["Kotlin", "Android"] → "["Kotlin","Android"]"
     *   emptyList()          → "[]"
     *   null                 → "[]"
     */
    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        // Never return null - return empty JSON array instead
        return if (value.isNullOrEmpty()) {
            "[]"
        } else {
            gson.toJson(value)
        }
    }

    /**
     * Converts JSON String → List<String> when reading from SQLite.
     *
     * Examples:
     *   "["Kotlin","Android"]" → ["Kotlin", "Android"]
     *   "[]"                   → emptyList()
     *   null                   → emptyList()
     *   ""                     → emptyList()
     *   "invalid json"         → emptyList()
     *
     * CRITICAL: Never return null! Room crashes if null is returned
     * for a non-nullable property.
     */
    @TypeConverter
    fun toStringList(value: String?): List<String> {
        // Handle null or blank input
        if (value.isNullOrBlank()) {
            return emptyList()
        }

        // Handle empty JSON array
        if (value == "[]") {
            return emptyList()
        }

        return try {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson<List<String>>(value, listType) ?: emptyList()
        } catch (e: Exception) {
            // If JSON parsing fails, log error and return empty list
            // (prevents crashes from corrupted data)
            if (com.microcareer.match.BuildConfig.DEBUG) {
                android.util.Log.e(
                    "ListConverters",
                    "Failed to parse string list: $value",
                    e
                )
            }
            emptyList()
        }
    }

    /* ── Future Converters (Add as needed) ───────────────────── */

    // Example: If you need Map<String, String> converter:
    /*
    @TypeConverter
    fun fromStringMap(value: Map<String, String>?): String {
        return if (value.isNullOrEmpty()) {
            "{}"
        } else {
            gson.toJson(value)
        }
    }

    @TypeConverter
    fun toStringMap(value: String?): Map<String, String> {
        if (value.isNullOrBlank() || value == "{}") {
            return emptyMap()
        }
        return try {
            val mapType = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson(value, mapType) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }
    */
}