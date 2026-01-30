package com.example.myapp.domain.model

data class Post(
    val id: Int,
    val avatar: String? = null,
    val postDate: Long,
    val firstName: String,
    val lastName: String,
    val images: List<String> = emptyList(),
    val commentsCount: Int,
    val likesCount: Int,
    val postDesc: String? = null,
    val canComment: Boolean,
    val canPostPhoto: Boolean
)