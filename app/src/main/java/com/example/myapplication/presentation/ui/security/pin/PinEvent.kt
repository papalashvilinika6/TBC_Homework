package com.example.myapplication.presentation.ui.security.pin

sealed class PinEvent {
    data class NumberPressed(val digit: Int) : PinEvent()
    object DeletePressed : PinEvent()
    object FingerprintPressed : PinEvent()
}