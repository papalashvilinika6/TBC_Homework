package com.example.myapplication.presentation.ui.favdriver

sealed interface DriversEvent {
    object LoadDrivers : DriversEvent
    data class SelectDriver(val id: Int) : DriversEvent
    object SaveAndNavigate : DriversEvent
}

