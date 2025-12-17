package com.example.myapplication.presentation.screen.race

import com.example.myapplication.presentation.model.RaceUiModel

data class RaceState(
    val isLoading: Boolean = false,
    val is2025: Boolean = true,
    val races: List<RaceUiModel> = emptyList()
)
