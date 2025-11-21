package com.example.myapplication.data.network

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("d7d9436b-21c5-43f7-82f9-2334163351cf")
    suspend fun getConversations(): Response<List<ChatConversationDto>>
}