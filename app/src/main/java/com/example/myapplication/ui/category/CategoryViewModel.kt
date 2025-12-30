package com.example.myapplication.ui.category

import com.example.myapplication.domain.model.Category
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.usecase.SearchCategoriesUseCase
import com.example.myapplication.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val searchCategoriesUseCase: SearchCategoriesUseCase
) : BaseViewModel<CategoryState, CategoryEvent>(
    initialState = CategoryState()
) {
    
    private var searchJob: Job? = null
    private var loadJob: Job? = null
    private val searchDebounceDelay = 500L

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.Load -> load()
            is CategoryEvent.SearchQueryChanged -> onSearchQueryChanged(event.query)
        }
    }

    private fun load() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            searchCategoriesUseCase(null).catch { e ->
                updateState {
                    it.copy(
                        loading = false,
                        error = e.message
                    )
                }
            }.collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        updateState { it.copy(loading = resource.isLoading, error = null) }
                    }
                    is Resource.Success -> {
                        updateState {
                            it.copy(
                                loading = false,
                                categories = flattenCategories(resource.data),
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        updateState {
                            it.copy(
                                loading = false,
                                error = resource.message
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onSearchQueryChanged(query: String) {
        searchJob?.cancel()
        
        searchJob = viewModelScope.launch {
            delay(searchDebounceDelay)
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            searchCategoriesUseCase(query.takeIf { it.isNotBlank() }).catch { e ->
                updateState {
                    it.copy(
                        loading = false,
                        error = e.message
                    )
                }
            }.collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        updateState { it.copy(loading = resource.isLoading, error = null) }
                    }
                    is Resource.Success -> {
                        updateState {
                            it.copy(
                                loading = false,
                                categories = flattenCategories(resource.data),
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        updateState {
                            it.copy(
                                loading = false,
                                error = resource.message
                            )
                        }
                    }
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

