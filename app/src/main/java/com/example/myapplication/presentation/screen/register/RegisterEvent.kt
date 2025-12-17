package com.example.myapplication.presentation.screen.register

sealed interface RegisterEvent {
    object CheckSession : RegisterEvent
    data class RegisterWithEmail(
        val email: String,
        val password: String,
        val name: String
    ) : RegisterEvent
    object SignOut : RegisterEvent
}