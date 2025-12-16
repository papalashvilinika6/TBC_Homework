package com.example.myapplication.data.remote

import com.example.myapplication.data.dto.DriversResponseDto
import retrofit2.http.GET

interface DriversApi {
    @GET("f2aa0798-4693-4d02-a237-844e75982442")
    suspend fun getDrivers(): DriversResponseDto
}