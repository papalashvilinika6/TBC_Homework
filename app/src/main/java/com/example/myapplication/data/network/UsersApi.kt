package com.example.myapplication.data.network

import com.example.myapplication.data.dto.UserResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UsersApi {
    @GET("api/users")
    suspend fun getUsers(@Query("page") page: Int): Response<UserResponseDto>
}