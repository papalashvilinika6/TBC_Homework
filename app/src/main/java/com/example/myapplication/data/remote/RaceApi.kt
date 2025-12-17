package com.example.myapplication.data.remote

import com.example.myapplication.data.dto.RaceDto
import retrofit2.http.GET

interface RaceApi {
    @GET("a45f6590-e750-4712-a43d-2ac942b007e9")
    suspend fun getRaces(): List<RaceDto>
}
