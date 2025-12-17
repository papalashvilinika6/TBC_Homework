package com.example.myapplication.domain.model

data class Race(
    val roundNum: Int,
    val countryImage: String,
    val country: String,
    val circuitName: String,
    val date2025: String,
    val date2026: String,
    val standings: List<String>,
    val time: List<String>
)