package com.example.myapplication.ui.map

import com.example.myapplication.domain.model.Place
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class PlaceClusterItem(val place: Place) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(place.latitude, place.longitude)
    override fun getTitle(): String = place.title
    override fun getSnippet(): String = place.description
    override fun getZIndex(): Float = 0f

    fun id(): Long = place.id
}

