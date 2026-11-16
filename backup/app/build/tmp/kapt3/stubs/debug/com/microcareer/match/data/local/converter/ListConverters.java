package com.microcareer.match.data.local.converter;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Room TypeConverters for complex types.
 *
 * CRITICAL: Room expects non-null returns. Returning null can cause
 * crashes when Room tries to populate entities. Always return safe
 * defaults (empty collections) instead of null.
 *
 * Used by: JobEntity, UserEntity, LearningModuleEntity, SkillProfileEntity
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\bH\u0007J\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/microcareer/match/data/local/converter/ListConverters;", "", "()V", "gson", "Lcom/google/gson/Gson;", "fromStringList", "", "value", "", "toStringList", "app_debug"})
public final class ListConverters {
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    
    public ListConverters() {
        super();
    }
    
    /**
     * Converts List<String> → JSON String for storage in SQLite.
     *
     * Examples:
     *  ["Kotlin", "Android"] → "["Kotlin","Android"]"
     *  emptyList()          → "[]"
     *  null                 → "[]"
     */
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromStringList(@org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> value) {
        return null;
    }
    
    /**
     * Converts JSON String → List<String> when reading from SQLite.
     *
     * Examples:
     *  "["Kotlin","Android"]" → ["Kotlin", "Android"]
     *  "[]"                   → emptyList()
     *  null                   → emptyList()
     *  ""                     → emptyList()
     *  "invalid json"         → emptyList()
     *
     * CRITICAL: Never return null! Room crashes if null is returned
     * for a non-nullable property.
     */
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> toStringList(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
}