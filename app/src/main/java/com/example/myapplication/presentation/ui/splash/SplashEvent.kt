package com.example.myapplication.presentation.ui.splash

sealed interface SplashEvent {
    data object OnStartSplash : SplashEvent
    data object OnStopSplash : SplashEvent

}