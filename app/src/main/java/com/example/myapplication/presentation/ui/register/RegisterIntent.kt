package com.example.myapplication.presentation.ui.register

sealed interface RegisterIntent {
    object CheckSession : RegisterIntent
    data class RegisterWithGoogle(val idToken: String) : RegisterIntent
    data class RegisterWithEmail(
        val email: String,
        val password: String,
        val name: String,
        val phone: String?
    ) : RegisterIntent
    object SignOut : RegisterIntent
}