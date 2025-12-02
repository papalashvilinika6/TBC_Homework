package com.example.myapplication.presentation.ui.security.pin

sealed class PinSideEffect {
    object Success : PinSideEffect()
    object Error : PinSideEffect()
}