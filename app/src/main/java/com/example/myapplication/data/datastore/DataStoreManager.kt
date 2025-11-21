package com.example.myapplication.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val REMEMBER_KEY = booleanPreferencesKey("remember_me")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? =
        context.dataStore.data.map { prefs -> prefs[TOKEN_KEY] }.first()

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(REMEMBER_KEY)
        }
    }

    suspend fun saveRememberMe(remember: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[REMEMBER_KEY] = remember
        }
    }

    suspend fun getRememberMe(): Boolean =
        context.dataStore.data.map { prefs -> prefs[REMEMBER_KEY] ?: false }.first()
}