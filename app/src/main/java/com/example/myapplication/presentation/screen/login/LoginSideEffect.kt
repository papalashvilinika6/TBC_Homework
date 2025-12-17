package com.example.myapplication.presentation.screen.login

sealed class LoginSideEffect {
    data object NavigateHome : LoginSideEffect()
}
