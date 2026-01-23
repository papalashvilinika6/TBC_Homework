package com.example.myapp.data.remote.mapper

import com.example.myapp.data.remote.dto.ChatDto
import com.example.myapp.domain.model.Chat

fun ChatDto.toDomain() = Chat(
    id = id,
    image = image.orEmpty(),
    owner = owner,
    lastMessage = lastMessage,
    lastActive = lastActive,
    unreadMessages = unreadMessages,
    isTyping = isTyping,
    lastMessageType = lastMessageType
)