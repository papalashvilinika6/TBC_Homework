package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Category
import com.example.myapplication.domain.repository.CategoryRepository
import javax.inject.Inject

class SearchCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(searchQuery: String?): Result<List<Category>> {
        val result = repository.searchCategories(searchQuery)
        return result.map { categories ->
            if (searchQuery.isNullOrBlank()) {
                categories
            } else {
                filterCategories(categories, searchQuery.lowercase())
            }
        }
    }

    private fun filterCategories(categories: List<Category>, query: String): List<Category> {
        val filtered = mutableListOf<Category>()
        
        fun searchInCategory(category: Category): Category? {
            val matchesName = category.name.lowercase().contains(query) || 
                             category.nameDe.lowercase().contains(query)
            
            val filteredChildren = category.children
                .mapNotNull { searchInCategory(it) }
                .sortedBy { it.orderId ?: Int.MAX_VALUE }
            
            return if (matchesName || filteredChildren.isNotEmpty()) {
                category.copy(children = filteredChildren)
            } else {
                null
            }
        }
        
        categories.forEach { category ->
            searchInCategory(category)?.let { filtered.add(it) }
        }
        
        return filtered.sortedBy { it.orderId ?: Int.MAX_VALUE }
    }
}

