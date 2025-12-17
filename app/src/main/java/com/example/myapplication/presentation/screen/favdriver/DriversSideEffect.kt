package com.example.myapplication.presentation.screen.favdriver

sealed interface DriversSideEffect {
    data class ShowError(val error: String) : DriversSideEffect
    object NavigateToHome : DriversSideEffect
}