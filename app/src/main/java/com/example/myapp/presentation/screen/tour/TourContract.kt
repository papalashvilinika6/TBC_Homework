package com.example.myapp.presentation.screen.tour

import com.example.myapp.presentation.screen.tour.model.TourUi


data class TourHomeState(
    val loader: Boolean = false,
    val tours: List<TourUi> = emptyList(),
    val error: String? = null
)

sealed interface TourHomeEvent {
    data class OnTourClick(val index: Int) : TourHomeEvent
    object OnRetryClick : TourHomeEvent
    data class SetDarkMode(val enabled: Boolean) : TourHomeEvent
}

sealed interface TourHomeSideEffect {
    data class NavigateToTour(val index: Int) : TourHomeSideEffect
}