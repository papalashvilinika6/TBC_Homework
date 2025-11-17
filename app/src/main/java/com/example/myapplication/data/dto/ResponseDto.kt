package com.example.myapplication.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)

@Serializable
data class RegisterResponseDto(
    val id: Int,
    val token: String
)
