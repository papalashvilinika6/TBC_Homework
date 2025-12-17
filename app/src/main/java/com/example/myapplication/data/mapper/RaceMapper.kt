package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.RaceDto
import com.example.myapplication.domain.model.Race

fun RaceDto.toDomain(): Race =
    Race(
        roundNum = roundNum,
        countryImage = countryImage,
        country = country,
        circuitName = circuitName,
        date2025 = date2025,
        date2026 = date2026,
        standings = standings,
        time = time
    )