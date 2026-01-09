package com.example.challenge.presentation.screen.splash

sealed class SplashUiEvent {
    data object NavigateToLogIn : SplashUiEvent()
    data object NavigateToConnections : SplashUiEvent()
}