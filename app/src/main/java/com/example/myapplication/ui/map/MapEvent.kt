package com.example.myapplication.ui.map

import com.example.myapplication.domain.model.Place

sealed interface MapEvent {
    data object Load : MapEvent
    data class SelectPlace(val place: Place?) : MapEvent
}

