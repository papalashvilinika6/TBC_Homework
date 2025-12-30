package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Category
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(searchQuery: String?): Flow<Resource<List<Category>>> {
        return repository.searchCategories(searchQuery).map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val categories = if (searchQuery.isNullOrBlank()) {
                        resource.data
                    } else {
                        filterCategories(resource.data, searchQuery.lowercase())
                    }
                    Resource.Success(categories)
                }
                is Resource.Error -> Resource.Error(resource.message)
                is Resource.Loader -> Resource.Loader(resource.isLoading)
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

