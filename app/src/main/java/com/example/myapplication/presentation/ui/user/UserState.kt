package com.example.myapplication.presentation.ui.user

import com.example.myapplication.data.local.UserData

data class UserState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val loadedUser: UserData? = null,
    val isLoading: Boolean = false
)
