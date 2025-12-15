package com.example.myapplication.data.repository

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.remote.StoryApi
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.model.Story
import com.example.myapplication.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class StoryRepositoryImpl @Inject constructor (
    private val api: StoryApi
) : StoryRepository {
    override fun getStories(): Flow<Resource<List<Story>>> =
        HandleResponse.safeApiCall { api.getStories() }
            .map { res ->
                when (res) {
                    is Resource.Success -> Resource.Success(res.data.map { it.toDomain() })
                    is Resource.Error   -> Resource.Error(res.message)
                    is Resource.Loader  -> Resource.Loader(res.isLoading)
                }
            }
}