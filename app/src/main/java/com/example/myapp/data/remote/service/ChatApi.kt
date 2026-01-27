package com.example.myapp.data.remote.service

import com.example.myapp.data.remote.dto.ChatDto
import retrofit2.Response
import retrofit2.http.GET

interface ChatApi {
    @GET(CHATS)
    suspend fun getChat(): Response<List<ChatDto>>


    companion object {
        const val CHATS = "chats"
    }
}
