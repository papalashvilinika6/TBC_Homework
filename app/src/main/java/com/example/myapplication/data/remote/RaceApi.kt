package com.example.myapplication.data.remote

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.dto.RaceDto
import retrofit2.http.GET

interface RaceApi {
    @GET(BuildConfig.RACE_ENDPOINT)
    suspend fun getRaces(): List<RaceDto>
}
