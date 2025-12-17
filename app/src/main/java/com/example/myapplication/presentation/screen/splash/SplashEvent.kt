package com.example.myapplication.presentation.screen.splash

sealed interface SplashEvent {
    data object OnStartSplash : SplashEvent
    data object OnStopSplash : SplashEvent
}