package com.example.myapplication.ui.category


data class CategoryState(
    val loading: Boolean = false,
    val error: String? = null,
    val categories: List<CategoryItem> = emptyList()
)

