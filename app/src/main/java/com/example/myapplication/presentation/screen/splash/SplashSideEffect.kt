package com.example.myapplication.presentation.screen.splash

sealed interface SplashSideEffect {
    data object NavigateToHome : SplashSideEffect
    data object NavigateToLogin : SplashSideEffect
}



