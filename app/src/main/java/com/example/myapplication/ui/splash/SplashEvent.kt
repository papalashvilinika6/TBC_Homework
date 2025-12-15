package com.example.myapplication.ui.splash

sealed interface SplashEvent {
    data object OnStartSplash : SplashEvent
    data object OnStopSplash : SplashEvent
}