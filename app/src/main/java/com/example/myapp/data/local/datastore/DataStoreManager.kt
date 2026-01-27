package com.example.myapp.data.local.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DataStoreManager {
    fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun <T> setPreference(key: Preferences.Key<T>, value: T)
}
