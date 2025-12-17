package com.example.myapplication.presentation.screen.profile

data class ProfileState(
    val isLoading: Boolean = false,
    val name: String = "",
    val email: String = ""
)
