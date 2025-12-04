package com.example.myapplication.presentation.ui.splash

sealed interface SplashSideEffect {
    data object NavigateToHome : SplashSideEffect
}



