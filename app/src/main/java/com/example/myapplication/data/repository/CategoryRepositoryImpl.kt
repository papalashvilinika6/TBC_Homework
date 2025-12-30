package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.common.HandleResponse
import com.example.myapplication.data.remote.mapper.toDomain
import com.example.myapplication.data.remote.service.ApiService
import com.example.myapplication.domain.model.Category
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val api: ApiService
) : CategoryRepository {

    override fun searchCategories(searchQuery: String?): Flow<Resource<List<Category>>> =
        HandleResponse.safeApiCall { api.getCategories(searchQuery) }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data.map { it.toDomain() })
                    is Resource.Error -> Resource.Error(resource.message)
                    is Resource.Loader -> Resource.Loader(resource.isLoading)
                }
            }
}

