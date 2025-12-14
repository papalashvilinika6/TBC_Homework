package com.example.myapplication.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DriversResponseDto(
    val drivers: List<DriverDto>
)
