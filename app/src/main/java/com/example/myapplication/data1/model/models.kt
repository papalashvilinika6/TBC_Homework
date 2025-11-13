package com.example.myapplication.data1.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val userName: String, val password: String)

@Serializable
data class RegisterRequest(val email: String, val userName: String, val password: String)

@Serializable
data class LoginResponse(val token: String)

@Serializable
data class RegisterResponse(val id: Int, val token: String)
