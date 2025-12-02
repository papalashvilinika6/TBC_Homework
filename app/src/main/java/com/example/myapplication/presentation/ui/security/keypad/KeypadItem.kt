package com.example.myapplication.presentation.ui.security.keypad

sealed class KeypadItem {
    data class Number(val value: Int) : KeypadItem()
    object Fingerprint : KeypadItem()
    object Delete : KeypadItem()
}