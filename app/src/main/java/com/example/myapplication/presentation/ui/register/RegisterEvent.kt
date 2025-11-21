package com.example.myapplication.presentation.ui.register

sealed interface RegisterEvent {
    data class OnEmailChanged(val email: String) : RegisterEvent
    data class OnPasswordChanged(val password: String) : RegisterEvent
    data class OnRepeatPasswordChanged(val password: String) : RegisterEvent
    data class Register(val email: String, val password: String) : RegisterEvent
    data object Success : RegisterEvent
}