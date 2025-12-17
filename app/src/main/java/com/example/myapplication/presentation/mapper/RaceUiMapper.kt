package com.example.myapplication.presentation.mapper

import com.example.myapplication.domain.model.Race
import com.example.myapplication.presentation.model.RaceUiModel

fun Race.toUi(is2025: Boolean): RaceUiModel =
    RaceUiModel(
        roundNum = roundNum,
        countryImage = countryImage,
        country = country,
        circuitName = circuitName,
        date = if (is2025) date2025 else date2026,
        standings = if (is2025) standings else null,
        time = if (is2025) time else null
    )