package com.example.myapplication.presentation.ui.user

sealed class UserEvent {
    data class Save(val firstName: String, val lastName: String, val email: String) : UserEvent()
    object Read : UserEvent()
}
