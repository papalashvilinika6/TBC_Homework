package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Category
import com.example.myapplication.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun searchCategories(searchQuery: String?): Flow<Resource<List<Category>>>
}

