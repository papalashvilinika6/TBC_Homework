package com.example.myapplication.ui.map

import com.example.myapplication.domain.model.Place
import com.google.android.gms.maps.model.LatLng

data class MapState(
    val loading: Boolean = false,
    val error: String? = null,
    val places: List<Place> = emptyList(),
    val myLocation: LatLng? = null,
    val selected: Place? = null
)

