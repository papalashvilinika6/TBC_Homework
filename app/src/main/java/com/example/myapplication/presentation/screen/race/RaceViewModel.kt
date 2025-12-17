package com.example.myapplication.presentation.screen.race

import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetRacesUseCase
import com.example.myapplication.presentation.screen.common.BaseViewModel
import com.example.myapplication.presentation.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceViewModel @Inject constructor(
    private val getRacesUseCase: GetRacesUseCase
) : BaseViewModel<RaceState, RaceEvent, RaceSideEffect>(
    initialState = RaceState()
) {

    override fun onEvent(event: RaceEvent) {
        when (event) {
            RaceEvent.Load2025 -> load(is2025 = true)
            RaceEvent.Load2026 -> load(is2025 = false)
        }
    }

    private fun load(is2025: Boolean) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                    is2025 = is2025
                )
            }

            runCatching {
                getRacesUseCase()
            }.onSuccess { races ->
                updateState {
                    it.copy(
                        isLoading = false,
                        races = races.map { race ->
                            race.toUi(is2025)
                        }
                    )
                }
            }.onFailure { throwable ->
                updateState { it.copy(isLoading = false) }
                emitSideEffect(
                    RaceSideEffect.ShowError(
                        throwable.message ?: "Something went wrong"
                    )
                )
            }
        }
    }
}
