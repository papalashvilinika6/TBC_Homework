package com.example.myapplication.data1.remote

import com.example.myapplication.data1.model.LoginRequest
import com.example.myapplication.data1.model.LoginResponse
import com.example.myapplication.data1.model.RegisterRequest
import com.example.myapplication.data1.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    suspend fun login(@Body request: LoginRequest) : LoginResponse

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse
}