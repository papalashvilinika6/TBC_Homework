package com.example.myapplication.domain.model

data class Post(
    val id: Int,
    val avatar: String,
    val fullName: String,
    val images: List<String>,
    val commentsCount: Int,
    val likesCount: Int,
    val description: String,
    val createdAtEpochMillis: Long,
    val canComment: Boolean,
    val canPostPhoto: Boolean
)
