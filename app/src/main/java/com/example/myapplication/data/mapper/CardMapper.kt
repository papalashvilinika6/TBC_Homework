package com.example.myapplication.data.mapper

import com.example.myapplication.data.remote.CardDto
import com.example.myapplication.domain.model.Card

fun CardDto.toDomain() = Card(
    location = location,
    altitude = altitudeM,
    title = title,
    image = image,
    stars = stars
)
