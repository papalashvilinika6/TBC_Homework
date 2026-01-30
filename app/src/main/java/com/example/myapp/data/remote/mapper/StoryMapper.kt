package com.example.myapp.data.remote.mapper

import com.example.myapp.data.remote.dto.StoryDto
import com.example.myapp.domain.model.Story

fun StoryDto.toDomain() = Story(
    id = id,
    title = title,
    cover = cover
)