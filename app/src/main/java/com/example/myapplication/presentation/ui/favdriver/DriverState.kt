package com.example.myapplication.presentation.ui.favdriver

data class DriversState(
    val isLoading: Boolean = false,
    val drivers: List<DriverUiModel> = emptyList(),
    val error: String? = null
)