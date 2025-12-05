package com.example.myapplication.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val location: String,
    @SerialName("altitude_m") val altitudeM: Int,
    val title: String,
    val image: String,
    val stars: Int
)
