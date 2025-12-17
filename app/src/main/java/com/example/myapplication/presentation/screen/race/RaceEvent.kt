package com.example.myapplication.presentation.screen.race

sealed class RaceEvent {
    data object Load2025 : RaceEvent()
    data object Load2026 : RaceEvent()
}