package com.example.myapplication.ui.map

import com.example.myapplication.domain.model.Place
import com.example.myapplication.domain.usecase.GetPlacesUseCase
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.ui.common.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.location.LocationClient
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getPlaces: GetPlacesUseCase,
    private val locationClient: LocationClient
) : BaseViewModel<MapState, MapEvent>(
    initialState = MapState()
) {

    override fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.Load -> load()
            is MapEvent.SelectPlace -> select(event.place)
        }
    }

    private fun load() {
        if (state.value.loading || state.value.places.isNotEmpty()) return
        viewModelScope.launch {
            getPlaces.stream().collect { st ->
                when (st) {
                    is Resource.Loading -> updateState { it.copy(loading = true, error = null) }
                    is Resource.Error -> updateState { it.copy(loading = false, error = st.message) }
                    is Resource.Success -> updateState { it.copy(loading = false, places = st.data, error = null) }
                }
            }
        }
        viewModelScope.launch {
            val loc = locationClient.getCurrentLocation()
            updateState { it.copy(myLocation = loc?.let { l -> LatLng(l.latitude, l.longitude) }) }
        }
    }

    private fun select(p: Place?) {
        updateState { it.copy(selected = p) }
    }
}

