package com.example.myapplication.presentation.ui.cards

data class CardState(
    val cards: List<CardUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
