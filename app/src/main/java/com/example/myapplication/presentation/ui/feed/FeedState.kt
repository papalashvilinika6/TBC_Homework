package com.example.myapplication.presentation.ui.feed

data class FeedState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<FeedItem> = emptyList(),
    val isOnline: Boolean = true
)

