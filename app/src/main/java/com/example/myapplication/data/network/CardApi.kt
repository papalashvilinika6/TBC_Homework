package com.example.myapplication.data.network

import com.example.myapplication.data.remote.CardDto
import retrofit2.Response
import retrofit2.http.GET

interface CardApi {

    @GET("e3215354-6784-4bae-9bb9-25b39360971b")
    suspend fun getCards(): Response<List<CardDto>>
}
