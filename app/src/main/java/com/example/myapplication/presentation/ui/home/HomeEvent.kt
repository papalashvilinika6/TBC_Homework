package com.example.myapplication.presentation.ui.home

sealed interface HomeEvent {
    data class FetchUsers(val page: Int = 1) : HomeEvent
}