package com.example.myapplication.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("auth_prefs")

object AuthKeys {
    val TOKEN = stringPreferencesKey("token")
    val REMEMBER_ME = booleanPreferencesKey("remember_me")
}

class AuthPreferences(private val context: Context) {

    val tokenFlow = context.dataStore.data.map { prefs ->
        prefs[AuthKeys.TOKEN] ?: ""
    }

    val rememberMeFlow = context.dataStore.data.map { prefs ->
        prefs[AuthKeys.REMEMBER_ME] ?: false
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[AuthKeys.TOKEN] = token
        }
    }

    suspend fun saveRememberMe(remember: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[AuthKeys.REMEMBER_ME] = remember
        }
    }
}
