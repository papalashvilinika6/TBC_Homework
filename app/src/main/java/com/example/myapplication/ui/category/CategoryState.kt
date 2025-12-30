package com.example.myapplication.ui.category

import com.example.myapplication.domain.model.Category

data class CategoryState(
    val loading: Boolean = false,
    val error: String? = null,
    val categories: List<CategoryItem> = emptyList()
)

data class CategoryItem(
    val category: Category
)

