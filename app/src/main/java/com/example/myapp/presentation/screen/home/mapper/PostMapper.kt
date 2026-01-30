package com.example.myapp.presentation.screen.home.mapper

import com.example.myapp.domain.model.Post
import com.example.myapp.presentation.extension.toPostDateText
import com.example.myapp.presentation.screen.home.model.PostUi

fun Post.toPresentation(): PostUi = PostUi(
    id = id,
    avatar = avatar,
    postDate = postDate.toPostDateText(),
    fullName = "$firstName $lastName",
    images = images,
    commentsCount = commentsCount,
    likesCount = likesCount,
    postDesc = postDesc,
    canComment = canComment,
    canPostPhoto = canPostPhoto
)