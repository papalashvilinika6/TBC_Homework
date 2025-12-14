package com.example.myapplication.presentation.ui.login

import com.example.myapplication.domain.model.User


data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)