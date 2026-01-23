package com.example.myapp.presentation.screen.home

import com.example.myapp.domain.model.Chat

data class ChatListState(
    val loader: Boolean = false,
    val queryInput: String = "",
    val queryApplied: String = "",
    val chats: List<Chat> = emptyList(),
    val filteredChats: List<Chat> = emptyList(),
    val error: String? = null
)

sealed interface ChatListEvent {
    data class OnQueryChanged(val value: String) : ChatListEvent
    data object OnSearchClick : ChatListEvent
    data class OnChatClick(val chatId: Int) : ChatListEvent
}

sealed interface ChatListSideEffect {
    data class NavigateToChat(val chatId: Int) : ChatListSideEffect
}