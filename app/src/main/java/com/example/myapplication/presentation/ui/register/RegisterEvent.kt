package com.example.myapplication.presentation.ui.register

sealed interface RegisterEvent {
    data class Register(val email: String, val password: String) : RegisterEvent
}