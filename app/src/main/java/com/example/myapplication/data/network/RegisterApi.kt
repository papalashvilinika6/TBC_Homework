package com.example.myapplication.data.network

import com.example.myapplication.data.dto.RegisterRequestDto
import com.example.myapplication.data.dto.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {

    @POST("api/register")
    suspend fun register(
        @Body registerRequest: RegisterRequestDto
    ): Response<RegisterResponseDto>

}