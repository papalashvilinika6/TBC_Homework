package com.example.myapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val id: Int,
    val title: String,
    val cover: String
)


