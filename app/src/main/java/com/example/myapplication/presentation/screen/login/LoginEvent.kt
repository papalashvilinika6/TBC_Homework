package com.example.myapplication.presentation.screen.login


sealed interface LoginEvent {
    object CheckSession : LoginEvent
    data class SignInWithEmail(val email: String, val password: String) : LoginEvent
}