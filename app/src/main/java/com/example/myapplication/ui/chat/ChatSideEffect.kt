package com.example.myapplication.ui.chat

sealed class ChatSideEffect {
    data class ShowError(val message: String) : ChatSideEffect()
}