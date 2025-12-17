package com.example.myapplication.presentation.screen.profile

sealed class ProfileEvent {
    data object LoadProfile : ProfileEvent()
    data object Logout : ProfileEvent()
    data object DeleteAccount : ProfileEvent()
}
