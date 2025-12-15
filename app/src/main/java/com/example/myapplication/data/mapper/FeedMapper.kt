package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.PostDto
import com.example.myapplication.data.dto.StoryDto
import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.model.Story

fun StoryDto.toDomain() = Story(
    id = title.hashCode(),
    title = title,
    cover = cover
)

fun PostDto.toDomain() = Post(
    id = (firstName + lastName + postDate + postDesc).hashCode(),
    avatar = avatar,
    fullName = "$firstName $lastName",
    images = images,
    commentsCount = commentsCount,
    likesCount = likesCount,
    description = postDesc,
    createdAtEpochMillis = postDate,
    canComment = canComment,
    canPostPhoto = canPostPhoto
)