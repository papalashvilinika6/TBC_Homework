package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.mapper.toDomain
import com.example.myapplication.data.remote.service.ApiService
import com.example.myapplication.domain.model.Category
import com.example.myapplication.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val api: ApiService,
    private val io: CoroutineDispatcher
) : CategoryRepository {

    override suspend fun searchCategories(searchQuery: String?): Result<List<Category>> = 
        withContext(io) {
            runCatching {
                api.getCategories(searchQuery).map { it.toDomain() }
            }
        }
}

