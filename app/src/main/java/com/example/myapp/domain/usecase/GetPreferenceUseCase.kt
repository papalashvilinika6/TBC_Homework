package com.example.myapp.domain.usecase

import androidx.datastore.preferences.core.Preferences
import com.example.myapp.data.local.datastore.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferenceUseCase @Inject constructor(
    private val preferencesRepository: DataStoreManager
)  {
    operator fun <T> invoke(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return preferencesRepository.getPreference(key, defaultValue)
    }
}