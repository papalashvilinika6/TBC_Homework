package com.example.myapplication.presentation.ui.feed

sealed class FeedSideEffect {
    data class ShowError(val message: String) : FeedSideEffect()
    object NoInternetBanner : FeedSideEffect()
}