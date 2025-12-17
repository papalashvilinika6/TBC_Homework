package com.example.myapplication.presentation.model

data class DriverUiModel(
    val id: Int,
    val fullName: String,
    val photoUrl: String,
    val backgroundColor: Int,
    val isFavorite: Boolean = false,
    val favDriverImage: String
)