package com.example.myapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: Int,
    val image: String?,
    val owner: String,
    @SerialName("last_message") val lastMessage: String,
    @SerialName("last_active") val lastActive: String,
    @SerialName("unread_messages") val unreadMessages: Int,
    @SerialName("is_typing") val isTyping: Boolean,
    @SerialName("last_message_type") val lastMessageType: String
)