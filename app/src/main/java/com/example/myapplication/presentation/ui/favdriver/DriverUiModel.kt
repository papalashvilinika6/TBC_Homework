package com.example.myapplication.presentation.ui.favdriver

data class DriverUiModel(
    val id: Int,
    val fullName: String,
    val photoUrl: String,
    val backgroundColor: Int,
    val isFavorite: Boolean = false
)

