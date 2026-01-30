package com.example.myapp.data.remote.mapper

import com.example.myapp.data.remote.dto.PostDto
import com.example.myapp.domain.model.Post

fun PostDto.toDomain() = Post(
    id = id,
    avatar = avatar,
    postDate = postDate,
    firstName = firstName,
    lastName = lastName,
    images = images,
    commentsCount = commentsCount,
    likesCount = likesCount,
    postDesc = postDesc,
    canComment = canComment,
    canPostPhoto = canPostPhoto
)