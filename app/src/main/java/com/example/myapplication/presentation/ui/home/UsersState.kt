package com.example.myapplication.presentation.ui.home

import com.example.myapplication.domain.model.User

data class UsersState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val isOnline: Boolean = true
)
