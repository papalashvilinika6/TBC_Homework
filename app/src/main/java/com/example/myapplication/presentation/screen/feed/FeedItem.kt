package com.example.myapplication.presentation.screen.feed

import com.example.myapplication.domain.model.Driver
import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.model.Story

sealed interface FeedItem {
    data class StoriesRow(val stories: List<Story>) : FeedItem
    data class MyDriverRow(val driver: Driver) : FeedItem
    data object FeedHeaderRow : FeedItem
    data class PostRow(val post: Post) : FeedItem
}