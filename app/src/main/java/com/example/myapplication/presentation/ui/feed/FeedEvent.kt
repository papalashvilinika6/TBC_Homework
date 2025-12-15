package com.example.myapplication.presentation.ui.feed

sealed class FeedEvent {
    object LoadFeed : FeedEvent()
}