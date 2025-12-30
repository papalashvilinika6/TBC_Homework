package com.example.myapplication.ui.category

import com.example.myapplication.domain.model.Category
import com.example.myapplication.domain.usecase.SearchCategoriesUseCase
import com.example.myapplication.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val searchCategoriesUseCase: SearchCategoriesUseCase
) : BaseViewModel<CategoryState, CategoryEvent>(
    initialState = CategoryState()
) {
    
    private var searchJob: Job? = null
    private val searchDebounceDelay = 500L

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.Load -> load()
            is CategoryEvent.SearchQueryChanged -> onSearchQueryChanged(event.query)
        }
    }

    private fun load() {
        if (state.value.loading) return
        viewModelScope.launch {
            updateState { it.copy(loading = true, error = null) }
            val result = searchCategoriesUseCase(null)
            result.getOrNull()?.let { categories ->
                updateState {
                    it.copy(
                        loading = false,
                        categories = flattenCategories(categories),
                        error = null
                    )
                }
            } ?: run {
                val exception = result.exceptionOrNull()
                updateState {
                    it.copy(
                        loading = false,
                        error = exception?.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    private fun onSearchQueryChanged(query: String) {
        // Cancel previous search job
        searchJob?.cancel()
        
        // Start new debounced search
        searchJob = viewModelScope.launch {
            delay(searchDebounceDelay)
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            updateState { it.copy(loading = true, error = null) }
            val result = searchCategoriesUseCase(query.takeIf { it.isNotBlank() })
            result.getOrNull()?.let { categories ->
                updateState {
                    it.copy(
                        loading = false,
                        categories = flattenCategories(categories),
                        error = null
                    )
                }
            } ?: run {
                val exception = result.exceptionOrNull()
                updateState {
                    it.copy(
                        loading = false,
                        error = exception?.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    private fun flattenCategories(categories: List<Category>): List<CategoryItem> {
        val result = mutableListOf<CategoryItem>()
        
        fun traverse(category: Category) {
            result.add(CategoryItem(category))
            category.children.forEach { child ->
                traverse(child)
            }
        }
        
        categories.forEach { category ->
            traverse(category)
        }
        
        return result
    }
}

