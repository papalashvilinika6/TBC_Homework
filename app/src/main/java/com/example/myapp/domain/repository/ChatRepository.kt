package com.example.myapp.domain.repository

import com.example.myapp.domain.model.Chat
import com.example.myapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<Resource<List<Chat>>>
}