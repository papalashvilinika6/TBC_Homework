package com.example.myapp.presentation.screen.home

data class ChatItemUi(
    val id: Int,
    val name: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
    val isOnline: Boolean
)