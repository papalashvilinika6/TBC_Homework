package com.example.myapplication.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val title: String,
    val cover: String
)