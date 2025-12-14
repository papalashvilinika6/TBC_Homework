package com.example.myapplication.presentation.ui.favdriver

sealed interface DriversEvent {
    object LoadDrivers : DriversEvent
    data class ToggleFavorite(val id: Int) : DriversEvent
}

