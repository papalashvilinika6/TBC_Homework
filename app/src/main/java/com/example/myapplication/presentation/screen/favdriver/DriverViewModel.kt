package com.example.myapplication.presentation.screen.favdriver

import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetCurrentUserUseCase
import com.example.myapplication.domain.usecase.GetDriversUseCase
import com.example.myapplication.domain.usecase.SaveFavoriteDriverUseCase
import com.example.myapplication.presentation.screen.common.BaseViewModel
import com.example.myapplication.presentation.mapper.DriverUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriversViewModel @Inject constructor(
    private val getDriversUseCase: GetDriversUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val saveFavoriteDriverUseCase: SaveFavoriteDriverUseCase,
    private val uiMapper: DriverUiMapper
) : BaseViewModel<DriversState, DriversEvent, DriversSideEffect>(
    initialState = DriversState()
) {

    override fun onEvent(event: DriversEvent) {
        when (event) {
            DriversEvent.LoadDrivers -> loadDrivers()
            is DriversEvent.SelectDriver -> selectDriver(event.id)
            DriversEvent.SaveAndNavigate -> saveAndNavigate()
        }
    }

    private fun loadDrivers() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

            runCatching {
                val domainDrivers = getDriversUseCase()
                val drivers = uiMapper.mapList(domainDrivers)
                
                updateState {
                    it.copy(
                        isLoading = false,
                        drivers = drivers,
                        selectedDriverId = null
                    )
                }
            }.onFailure { throwable ->
                updateState { it.copy(isLoading = false) }
                emitSideEffect(
                    DriversSideEffect.ShowError(
                        throwable.message.orEmpty()
                    )
                )
            }
        }
    }

    private fun selectDriver(id: Int) {
        updateState { state ->
            state.copy(
                selectedDriverId = id,
                drivers = state.drivers.map { driver ->
                    driver.copy(isFavorite = driver.id == id)
                }
            )
        }
    }

    private fun saveAndNavigate() {
        viewModelScope.launch {
            val currentUser = getCurrentUserUseCase().first()

            if (currentUser == null) {
                return@launch
            }

            val selectedDriverId = state.value.selectedDriverId

            if (selectedDriverId == null) {
                return@launch
            }

            val result = saveFavoriteDriverUseCase(currentUser.uid, selectedDriverId)

            if (result.isSuccess) {
                emitSideEffect(DriversSideEffect.NavigateToHome)
            } else {
                val errorMessage = result.exceptionOrNull()?.message.orEmpty()
                emitSideEffect(
                    DriversSideEffect.ShowError(errorMessage)
                )
            }
        }
    }
}

