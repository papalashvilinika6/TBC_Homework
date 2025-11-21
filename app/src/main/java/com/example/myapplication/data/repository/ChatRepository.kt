package com.example.myapplication.data.repository

import com.example.myapplication.data.network.ApiService
import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.network.ChatConversationDto
import com.example.myapplication.data.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val apiService: ApiService,
    private val handleResponse: HandleResponse
) {

    fun getConversations(): Flow<Resource<List<ChatConversationDto>>> {
        return handleResponse.safeApiCall {
            apiService.getConversations()
        }
    }
}