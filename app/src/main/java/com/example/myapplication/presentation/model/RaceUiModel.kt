package com.example.myapplication.presentation.model

data class RaceUiModel(
    val roundNum: Int,
    val countryImage: String,
    val country: String,
    val circuitName: String,
    val date: String,
    val standings: List<String>?,
    val time: List<String>?
)