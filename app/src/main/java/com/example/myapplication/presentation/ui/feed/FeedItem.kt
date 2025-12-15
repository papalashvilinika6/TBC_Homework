package com.example.myapplication.presentation.ui.feed

import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.model.Story

sealed class FeedItem {
    data class StoriesRow(val stories: List<Story>) : FeedItem()
    data class PostRow(val post: Post) : FeedItem()
}