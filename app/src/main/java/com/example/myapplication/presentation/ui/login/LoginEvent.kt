package com.example.myapplication.presentation.ui.login

sealed interface LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent
    data class OnPasswordChanged(val password: String) : LoginEvent
    data class Login(val email: String, val password: String, val rememberMe: Boolean) : LoginEvent
    data object ClearToken : LoginEvent
    data object EmitSuccessNavigation : LoginEvent
    data object Success : LoginEvent

}