package com.example.myapp.presentation.screen.tour

import app.cash.turbine.test
import com.example.myapp.data.local.datastore.PreferencesKeys
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.model.Tour
import com.example.myapp.domain.usecase.GetPreferenceUseCase
import com.example.myapp.domain.usecase.GetToursUseCase
import com.example.myapp.domain.usecase.SetPreferenceUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TourHomeViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val getToursUseCase: GetToursUseCase = mockk()
    private val getPreferenceUseCase: GetPreferenceUseCase = mockk()
    private val setPreferenceUseCase: SetPreferenceUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        clearAllMocks()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init - Loader true then Loader false then Success updates state`() = runTest {
        val toursFlow = MutableSharedFlow<Resource<List<Tour>>>()

        every { getToursUseCase.invoke() } returns toursFlow
        every { getPreferenceUseCase.invoke(PreferencesKeys.IS_DARK_MODE, false) } returns flowOf(false)
        coEvery { setPreferenceUseCase.invoke(any(), any<Boolean>()) } just Runs

        val vm = TourHomeViewModel(getToursUseCase, getPreferenceUseCase, setPreferenceUseCase)

        vm.state.test {
            val initial = awaitItem()
            assertFalse(initial.loader)
            assertTrue(initial.tours.isEmpty())
            assertNull(initial.error)

            toursFlow.emit(Resource.Loader(true))
            val loading = awaitItem()
            assertTrue(loading.loader)

            toursFlow.emit(Resource.Loader(false))
            val notLoading = awaitItem()
            assertFalse(notLoading.loader)

            val data = listOf(
                Tour(
                    title = "Natural walk",
                    location = "Barcelona",
                    number = 2500,
                    photo = "url",
                    price = 120,
                    stars = 4
                )
            )
            toursFlow.emit(Resource.Success(data))

            val success = awaitItem()
            assertFalse(success.loader)
            assertNull(success.error)
            assertEquals(1, success.tours.size)
            assertEquals("Natural walk", success.tours[0].title)
            assertEquals("Barcelona", success.tours[0].location)
            assertEquals(2500, success.tours[0].number)
            assertEquals(120, success.tours[0].price)
            assertEquals(4, success.tours[0].stars)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init - Error sets error and loader false`() = runTest {
        val toursFlow = MutableSharedFlow<Resource<List<Tour>>>()

        every { getToursUseCase.invoke() } returns toursFlow
        every { getPreferenceUseCase.invoke(PreferencesKeys.IS_DARK_MODE, false) } returns flowOf(false)
        coEvery { setPreferenceUseCase.invoke(any(), any<Boolean>()) } just Runs

        val vm = TourHomeViewModel(getToursUseCase, getPreferenceUseCase, setPreferenceUseCase)

        vm.state.test {
            awaitItem()

            toursFlow.emit(Resource.Loader(true))
            awaitItem()

            toursFlow.emit(Resource.Error(message = "Network error"))
            val err = awaitItem()

            assertEquals("Network error", err.error)
            assertFalse(err.loader)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnTourClick emits NavigateToTour`() = runTest {
        every { getToursUseCase.invoke() } returns flowOf(Resource.Success(emptyList()))
        every { getPreferenceUseCase.invoke(PreferencesKeys.IS_DARK_MODE, false) } returns flowOf(false)
        coEvery { setPreferenceUseCase.invoke(any(), any<Boolean>()) } just Runs

        val vm = TourHomeViewModel(getToursUseCase, getPreferenceUseCase, setPreferenceUseCase)

        vm.sideEffect.test {
            vm.onEvent(TourHomeEvent.OnTourClick(index = 3))
            val effect = awaitItem()
            assertTrue(effect is TourHomeSideEffect.NavigateToTour)
            assertEquals(3, (effect as TourHomeSideEffect.NavigateToTour).index)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnRetryClick calls getToursUseCase again`() = runTest {
        val toursFlow = MutableSharedFlow<Resource<List<Tour>>>()

        every { getToursUseCase.invoke() } returns toursFlow
        every { getPreferenceUseCase.invoke(PreferencesKeys.IS_DARK_MODE, false) } returns flowOf(false)
        coEvery { setPreferenceUseCase.invoke(any(), any<Boolean>()) } just Runs

        val vm = TourHomeViewModel(getToursUseCase, getPreferenceUseCase, setPreferenceUseCase)

        verify(exactly = 1) { getToursUseCase.invoke() }

        vm.onEvent(TourHomeEvent.OnRetryClick)
        advanceUntilIdle()

        verify(exactly = 2) { getToursUseCase.invoke() }
    }

    @Test
    fun `SetDarkMode writes to preferences`() = runTest {
        every { getToursUseCase.invoke() } returns flowOf(Resource.Success(emptyList()))
        every { getPreferenceUseCase.invoke(PreferencesKeys.IS_DARK_MODE, false) } returns flowOf(false)
        coEvery { setPreferenceUseCase.invoke(PreferencesKeys.IS_DARK_MODE, true) } just Runs

        val vm = TourHomeViewModel(getToursUseCase, getPreferenceUseCase, setPreferenceUseCase)

        vm.onEvent(TourHomeEvent.SetDarkMode(enabled = true))
        advanceUntilIdle()

        coVerify(exactly = 1) { setPreferenceUseCase.invoke(PreferencesKeys.IS_DARK_MODE, true) }
    }

    @Test
    fun `isDarkMode emits updates from preference flow`() = runTest {
        every { getToursUseCase.invoke() } returns flowOf(Resource.Success(emptyList()))
        val prefFlow = MutableStateFlow(false)
        every { getPreferenceUseCase.invoke(PreferencesKeys.IS_DARK_MODE, false) } returns prefFlow
        coEvery { setPreferenceUseCase.invoke(any(), any<Boolean>()) } just Runs

        val vm = TourHomeViewModel(getToursUseCase, getPreferenceUseCase, setPreferenceUseCase)

        vm.isDarkMode.test {
            assertEquals(false, awaitItem())
            prefFlow.value = true
            assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
