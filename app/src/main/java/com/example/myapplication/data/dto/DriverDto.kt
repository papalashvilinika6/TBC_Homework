package com.example.myapplication.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DriverDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val photo: String,
    val color: String
)
