package com.example.myapplication.domain.model

data class Post(
    val id: Int,
    val avatar: String,
    val fullName: String,
    val images: List<String>,
    val likesCount: Int,
    val description: String,
    val createdAtEpochMillis: Long
)
