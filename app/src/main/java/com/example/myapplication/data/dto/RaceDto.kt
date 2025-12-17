package com.example.myapplication.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceDto(
    @SerialName("round_num") val roundNum: Int,
    @SerialName("country_image") val countryImage: String,
    val country: String,
    @SerialName("circuit_name") val circuitName: String,
    @SerialName("date_2025") val date2025: String,
    @SerialName("date_2026") val date2026: String,
    val standings: List<String>,
    val time: List<String>
)
