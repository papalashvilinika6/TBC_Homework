package com.example.myapplication.presentation.screen.favdriver

import com.example.myapplication.presentation.model.DriverUiModel

data class DriversState(
    val isLoading: Boolean = false,
    val drivers: List<DriverUiModel> = emptyList(),
    val selectedDriverId: Int? = null,
    val error: String? = null
)