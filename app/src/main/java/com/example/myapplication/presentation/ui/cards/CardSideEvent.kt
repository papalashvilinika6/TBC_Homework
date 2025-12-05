package com.example.myapplication.presentation.ui.cards

sealed interface CardSideEffect {
    data object ShowError : CardSideEffect
}