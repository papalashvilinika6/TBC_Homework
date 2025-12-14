package com.example.myapplication.presentation.ui.login


sealed interface LoginIntent {
    object CheckSession : LoginIntent
    data class SignInWithGoogle(val idToken: String) : LoginIntent
    data class SignInWithEmail(val email: String, val password: String) : LoginIntent
    object SignOut : LoginIntent
}