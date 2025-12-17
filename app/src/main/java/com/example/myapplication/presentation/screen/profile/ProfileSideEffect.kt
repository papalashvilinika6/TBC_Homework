package com.example.myapplication.presentation.screen.profile

sealed class ProfileSideEffect {
    data object NavigateLogin : ProfileSideEffect()
    data class ShowError(val message: String) : ProfileSideEffect()
}
