package com.example.myapplication.presentation.screen.race

sealed class RaceSideEffect {
    data class ShowError(val message: String) : RaceSideEffect()
}