package com.example.myapplication.ui.category

sealed interface CategoryEvent {
    data object Load : CategoryEvent
    data class SearchQueryChanged(val query: String) : CategoryEvent
}

