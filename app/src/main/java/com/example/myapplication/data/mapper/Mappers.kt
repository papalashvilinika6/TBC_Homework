package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.PlaceDto
import com.example.myapplication.data.local.PlaceEntity
import com.example.myapplication.domain.model.Place

fun PlaceDto.toEntity() =
    PlaceEntity(
        id,
        title,
        description,
        latitude,
        longitude,
        imageUrl
    )
fun PlaceEntity.toDomain() =
    Place(
        id,
        title,
        description,
        latitude,
        longitude,
        imageUrl
    )

