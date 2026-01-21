package com.example.myapp.presentation.screen.feed

sealed class FeedEvent {
    object LoadProducts : FeedEvent()
    object Refresh : FeedEvent()
    data class SelectCategory(val category: String?) : FeedEvent()
    data class ToggleFavorite(val productId: Int) : FeedEvent()
}