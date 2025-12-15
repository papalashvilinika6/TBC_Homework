package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.LocationRepository
import com.example.myapplication.domain.model.Place
import com.example.myapplication.domain.model.Resource
import kotlinx.coroutines.flow.*

class GetPlacesUseCase(private val repo: LocationRepository) {
    fun stream(): Flow<Resource<List<Place>>> =
        repo.observePlaces()
            .map<List<Place>, Resource<List<Place>>> { Resource.Success(it) }
            .onStart {
                emit(Resource.Loading)
                repo.refresh().onFailure { emit(Resource.Error(it.message ?: "Unknown error")) }
            }
            .catch { emit(Resource.Error(it.message ?: "Unknown error")) }
}

