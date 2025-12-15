package com.example.myapplication.ui.splash

sealed interface SplashSideEffect {
    data object NavigateToMap : SplashSideEffect
}