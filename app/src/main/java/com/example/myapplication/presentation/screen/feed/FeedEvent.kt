package com.example.myapplication.presentation.screen.feed

sealed class FeedEvent {
    object LoadFeed : FeedEvent()
}