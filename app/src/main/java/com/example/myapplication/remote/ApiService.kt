package com.example.myapplication.remote

import com.example.myapplication.model.LoginRequest
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.model.RegisterRequest
import com.example.myapplication.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    suspend fun login(@Body request: LoginRequest) : LoginResponse

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse
}