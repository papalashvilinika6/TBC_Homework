package com.example.myapplication.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.myapplication.data.local.AuthKeys.REMEMBER_ME
import com.example.myapplication.data.local.AuthKeys.TOKEN
import com.example.myapplication.domain.repository.AuthLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthLocalRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AuthLocalRepository {

    override fun getToken(): Flow<String> =
        dataStore.data.map { prefs ->
            prefs[TOKEN] ?: ""
        }

    override fun isRemembered(): Flow<Boolean> =
        dataStore.data.map { prefs ->
            prefs[REMEMBER_ME] ?: false
        }

    override suspend fun saveToken(token: String) {
        dataStore.edit { prefs ->
            prefs[TOKEN] = token
        }
    }

    override suspend fun saveRememberMe(remember: Boolean) {
        dataStore.edit { prefs ->
            prefs[REMEMBER_ME] = remember
        }
    }
}
