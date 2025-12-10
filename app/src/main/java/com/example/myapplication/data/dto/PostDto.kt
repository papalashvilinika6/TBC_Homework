package com.example.myapplication.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val avatar: String,
    @SerialName("postDate") val postDate: Long,
    val firstName: String,
    val lastName: String,
    val images: List<String> = emptyList(),
    @SerialName("commentsCount") val commentsCount: Int,
    @SerialName("likesCount") val likesCount: Int,
    @SerialName("postDesc") val postDesc: String,
    @SerialName("canComment") val canComment: Boolean,
    @SerialName("canPostPhoto") val canPostPhoto: Boolean
)

