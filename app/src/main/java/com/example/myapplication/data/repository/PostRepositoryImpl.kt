package com.example.myapplication.data.repository

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.remote.PostApi
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: PostApi
) : PostRepository {

    override fun getPosts(): Flow<Resource<List<Post>>> =
        HandleResponse.safeApiCall { api.getPosts() }
            .map { res ->
                when (res) {
                    is Resource.Success -> Resource.Success(res.data.map { it.toDomain() })
                    is Resource.Error   -> Resource.Error(res.message)
                    is Resource.Loader  -> Resource.Loader(res.isLoading)
                }
            }
}