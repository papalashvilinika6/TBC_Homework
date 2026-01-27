package com.example.myapp.domain.usecase

import app.cash.turbine.test
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.myapp.data.local.datastore.DataStoreManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetPreferenceUseCaseTest {

    private val dataStoreManager: DataStoreManager = mockk()

    @Test
    fun `invoke - delegates to DataStoreManager and returns same value`() = runTest {
        val key = booleanPreferencesKey("is_dark_mode")
        val defaultValue = false
        val expected = true

        every { dataStoreManager.getPreference(key, defaultValue) } returns flowOf(expected)

        val useCase = GetPreferenceUseCase(dataStoreManager)

        useCase(key, defaultValue).test {
            assertEquals(expected, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        verify(exactly = 1) { dataStoreManager.getPreference(key, defaultValue) }
    }

    @Test
    fun `invoke - returns default when datastore emits default`() = runTest {
        val key = booleanPreferencesKey("is_dark_mode")
        val defaultValue = false

        every { dataStoreManager.getPreference(key, defaultValue) } returns flowOf(defaultValue)

        val useCase = GetPreferenceUseCase(dataStoreManager)

        useCase(key, defaultValue).test {
            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        verify(exactly = 1) { dataStoreManager.getPreference(key, defaultValue) }
    }
}
