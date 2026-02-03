package com.example.myapp.data.remote.service

import com.example.myapp.data.remote.dto.RegisterFieldDto
import retrofit2.Response
import retrofit2.http.GET

interface RegisterApi {
    @GET("/")
    suspend fun getRegisterConfig(): Response<List<List<RegisterFieldDto>>>
}
