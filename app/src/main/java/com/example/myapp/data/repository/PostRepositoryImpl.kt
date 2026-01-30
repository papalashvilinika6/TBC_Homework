package com.example.myapp.data.repository

import com.example.myapp.data.remote.common.HandleResponse
import com.example.myapp.data.remote.mapper.toDomain
import com.example.myapp.data.remote.service.PostApi
import com.example.myapp.domain.model.Post
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class PostRepositoryImpl @Inject constructor(
    val api: PostApi
): PostRepository {
    override fun getPosts(): Flow<Resource<List<Post>>> =
        HandleResponse.safeApiCall { api.getPosts() }
            .map { resource ->
                when(resource) {
                    is Resource.Error -> Resource.Error(resource.message)
                    is Resource.Loader -> Resource.Loader(resource.isLoading)
                    is Resource.Success -> Resource.Success(resource.data.map { it.toDomain() })
                }
            }


}