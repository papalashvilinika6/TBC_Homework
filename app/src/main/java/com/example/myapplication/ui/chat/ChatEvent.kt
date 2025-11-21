package com.example.myapplication.ui.chat

sealed class ChatEvent {
    object Load : ChatEvent()
    object OnSearchButtonClicked : ChatEvent()
    data class OnSearchQueryChanged(val query: String) : ChatEvent()
}
