package com.example.myapplication.data.remote

import com.example.myapplication.data.dto.DriversResponseDto
import retrofit2.http.GET

interface DriversApi {
    @GET("4932c17f-f218-415a-9875-4831f836723b")
    suspend fun getDrivers(): DriversResponseDto
}