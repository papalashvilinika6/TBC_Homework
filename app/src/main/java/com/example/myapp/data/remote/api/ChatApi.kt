package com.example.myapp.data.remote.api

import com.example.myapp.data.remote.dto.ChatDto
import retrofit2.Response
import retrofit2.http.GET

interface ChatApi {
    @GET("chats")
    suspend fun getChat(): Response<List<ChatDto>>
}