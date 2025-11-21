package com.example.myapplication.ui.chat

import com.example.myapplication.data.network.ChatConversationDto

data class ChatState(
    val isLoading: Boolean = false,
    val conversations: List<ChatConversationDto> = emptyList(),
    val filteredConversations: List<ChatConversationDto> = emptyList(),
    val isSearchEnabled: Boolean = false,
    val searchQuery: String = "",
    val errorMessage: String? = null
)
