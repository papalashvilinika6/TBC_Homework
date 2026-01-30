package com.example.myapp.data.repository

import com.example.myapp.data.remote.common.HandleResponse
import com.example.myapp.data.remote.mapper.toDomain
import com.example.myapp.data.remote.service.StoryApi
import com.example.myapp.domain.model.Story
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val api: StoryApi
): StoryRepository {
    override fun getStories(): Flow<Resource<List<Story>>> =
        HandleResponse.safeApiCall { api.getStories() }
            .map { resource ->
                when(resource){
                    is Resource.Success -> Resource.Success(resource.data.map { it.toDomain() })
                    is Resource.Loader -> Resource.Loader(resource.isLoading)
                    is Resource.Error -> Resource.Error(resource.message)
                }
            }

}