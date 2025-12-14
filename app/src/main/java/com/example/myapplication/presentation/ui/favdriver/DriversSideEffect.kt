package com.example.myapplication.presentation.ui.favdriver

sealed interface DriversSideEffect {
    data class ShowError(val message: String) : DriversSideEffect
}