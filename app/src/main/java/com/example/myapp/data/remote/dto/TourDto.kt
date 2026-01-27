package com.example.myapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TourDto(
    val title: String,
    val location: String,
    val number: Int,
    val photo: String,
    val price: Int,
    val stars: Int
)