package com.example.myapplication.presentation.screen.register

sealed class RegisterSideEffect {
    data object NavigateHome : RegisterSideEffect()
}
