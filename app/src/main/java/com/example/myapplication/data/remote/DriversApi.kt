package com.example.myapplication.data.remote

import com.example.myapplication.data.dto.DriversResponseDto
import retrofit2.http.GET

interface DriversApi {
    @GET("1168ef2b-4423-4e5f-9043-a3f9e93d4807")
    suspend fun getDrivers(): DriversResponseDto
}