package com.example.myapp.presentation.screen.tour

import androidx.lifecycle.viewModelScope
import com.example.myapp.data.local.datastore.PreferencesKeys
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.usecase.GetPreferenceUseCase
import com.example.myapp.domain.usecase.GetToursUseCase
import com.example.myapp.domain.usecase.SetPreferenceUseCase
import com.example.myapp.presentation.common.BaseViewModel
import com.example.myapp.presentation.screen.tour.TourHomeSideEffect.NavigateToTour
import com.example.myapp.presentation.screen.tour.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourHomeViewModel @Inject constructor(
    private val getToursUseCase: GetToursUseCase,
    getPreference: GetPreferenceUseCase,
    private val setPreference: SetPreferenceUseCase
) : BaseViewModel<TourHomeState, TourHomeEvent>(
    initialState = TourHomeState()
) {

    private val _sideEffect = Channel<TourHomeSideEffect>(Channel.BUFFERED)
    val sideEffect: Flow<TourHomeSideEffect> = _sideEffect.receiveAsFlow()

    val isDarkMode: StateFlow<Boolean> =
        getPreference(PreferencesKeys.IS_DARK_MODE, false)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    init {
        load()
    }

    override fun onEvent(event: TourHomeEvent) {
        when (event) {
            is TourHomeEvent.OnTourClick -> {
                viewModelScope.launch {
                    _sideEffect.send(NavigateToTour(event.index))
                }
            }

            TourHomeEvent.OnRetryClick -> load()

            is TourHomeEvent.SetDarkMode -> setDarkMode(event.enabled)
        }
    }

    private fun load() {
        viewModelScope.launch {
            getToursUseCase().collectLatest { res ->
                when (res) {
                    is Resource.Loader -> {
                        updateState { it.copy(loader = res.isLoading) }
                    }

                    is Resource.Success -> {
                        val uiList = res.data.map { it.toUi() }
                        updateState { it.copy(tours = uiList, error = null, loader = false) }
                    }

                    is Resource.Error -> {
                        updateState {
                            it.copy(
                                error = res.message,
                                loader = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            setPreference(PreferencesKeys.IS_DARK_MODE, enabled)
        }
    }
}
