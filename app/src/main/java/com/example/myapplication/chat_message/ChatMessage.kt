package com.example.myapplication.chat_message

data class ChatMessage(
    val id: Int,
    val text: String,
    val date: String,
    val isSentByMe: Boolean
)