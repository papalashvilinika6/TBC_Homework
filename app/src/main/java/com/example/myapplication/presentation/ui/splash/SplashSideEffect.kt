package com.example.myapplication.presentation.ui.splash

sealed interface SplashSideEffect {
    data object NavigateToSecurity : SplashSideEffect
}



