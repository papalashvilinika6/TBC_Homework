package com.example.myapplication.presentation.ui

import com.example.myapplication.presentation.ui.cards.CardUi

sealed interface CardEvent {
    data object Load : CardEvent
}

