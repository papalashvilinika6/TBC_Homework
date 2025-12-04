package com.example.myapplication.presentation.ui.home

sealed class UsersEvent {
    object Load : UsersEvent()
    object Refresh : UsersEvent()
}
