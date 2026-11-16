package com.microcareer.match.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProvider @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    // ✅ Get the current token
    suspend fun getToken(): String? {
        return dataStore.data.map { prefs -> prefs[TOKEN_KEY] }.firstOrNull()
    }

    // ✅ Save new tokens
    suspend fun saveTokens(token: String, refreshToken: String?) {
        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            refreshToken?.let { prefs[REFRESH_TOKEN_KEY] = it }
        }
    }

    // ✅ Clear tokens (logout)
    suspend fun clearTokens() {
        dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }
}