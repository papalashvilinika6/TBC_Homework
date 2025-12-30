package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Category

interface CategoryRepository {
    suspend fun searchCategories(searchQuery: String?): Result<List<Category>>
}

