package com.example.myapp.domain.usecase

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.myapp.data.local.datastore.DataStoreManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SetPreferenceUseCaseTest {

    private val dataStoreManager: DataStoreManager = mockk()

    @Test
    fun `invoke calls datastore setPreference`() = runTest {
        val key = booleanPreferencesKey("is_dark_mode")
        val value = true

        coEvery { dataStoreManager.setPreference(key, value) } returns Unit

        val useCase = SetPreferenceUseCase(dataStoreManager)

        useCase(key, value)

        coVerify(exactly = 1) { dataStoreManager.setPreference(key, value) }
    }
}
