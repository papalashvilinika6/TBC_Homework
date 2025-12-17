package com.example.myapplication.data.remote

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.dto.DriversResponseDto
import retrofit2.http.GET

interface DriversApi {
    @GET(BuildConfig.DRIVERS_ENDPOINT)
    suspend fun getDrivers(): DriversResponseDto
}