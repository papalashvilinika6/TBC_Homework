package com.example.myapp.presentation.screen.home.mapper

import com.example.myapp.domain.model.Story
import com.example.myapp.presentation.screen.home.model.StoryUi

fun Story.toPresentation(): StoryUi = StoryUi(
    id = id,
    title = title,
    cover = cover
)