package com.example.myapplication.presentation.screen.register

import com.example.myapplication.domain.model.User

data class RegisterState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)