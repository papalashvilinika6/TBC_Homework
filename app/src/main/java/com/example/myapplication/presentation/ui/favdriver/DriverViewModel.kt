package com.example.myapplication.presentation.ui.favdriver

import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetDriversUseCase
import com.example.myapplication.presentation.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriversViewModel @Inject constructor(
    private val getDriversUseCase: GetDriversUseCase,
    private val uiMapper: DriverUiMapper
) : BaseViewModel<DriversState, DriversEvent, DriversSideEffect>(
    initialState = DriversState()
) {

    override fun onEvent(event: DriversEvent) {
        when (event) {
            DriversEvent.LoadDrivers -> loadDrivers()
            is DriversEvent.ToggleFavorite -> toggleFavorite(event.id)
        }
    }

    private fun loadDrivers() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

            runCatching {
                getDriversUseCase()
            }.onSuccess { domainDrivers ->
                updateState {
                    it.copy(
                        isLoading = false,
                        drivers = uiMapper.mapList(domainDrivers)
                    )
                }
            }.onFailure { throwable ->
                updateState { it.copy(isLoading = false) }
                emitSideEffect(
                    DriversSideEffect.ShowError(
                        throwable.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    private fun toggleFavorite(id: Int) {
        updateState { state ->
            state.copy(
                drivers = state.drivers.map { driver ->
                    if (driver.id == id) {
                        driver.copy(isFavorite = !driver.isFavorite)
                    } else {
                        driver
                    }
                }
            )
        }
    }
}

