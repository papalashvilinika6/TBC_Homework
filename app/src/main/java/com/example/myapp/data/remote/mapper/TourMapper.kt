package com.example.myapp.data.remote.mapper

import com.example.myapp.data.remote.dto.TourDto
import com.example.myapp.domain.model.Tour

fun TourDto.toDomain() = Tour(
    title = title,
    location = location,
    number = number,
    photo = photo,
    price = price,
    stars = stars,
)