package com.example.myapp.data.repository

import com.example.myapp.data.remote.service.TourApi
import com.example.myapp.data.remote.common.HandleResponse
import com.example.myapp.data.remote.mapper.toDomain
import com.example.myapp.domain.model.Tour
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.TourRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TourRepositoryImpl @Inject constructor(
    private val api: TourApi
): TourRepository {
    override fun getTours(): Flow<Resource<List<Tour>>> =
        HandleResponse.safeApiCall { api.getTours() }
            .map { resource ->
                when(resource){
                    is Resource.Success -> Resource.Success(resource.data.map { it.toDomain() })
                    is Resource.Loader -> Resource.Loader(resource.isLoading)
                    is Resource.Error -> Resource.Error(resource.message)
                }
            }

}