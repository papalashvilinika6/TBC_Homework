package com.example.myapplication.data.remote

import com.example.myapplication.data.dto.PlaceDto
import retrofit2.http.GET

interface ApiService {
    @GET("v1/d7c6d734-6080-4045-a196-7da16339b6d7")
    suspend fun getPlaces(): List<PlaceDto>
}


