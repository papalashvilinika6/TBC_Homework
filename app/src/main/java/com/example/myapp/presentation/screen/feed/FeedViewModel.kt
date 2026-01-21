package com.example.myapp.presentation.screen.feed

import androidx.lifecycle.viewModelScope
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.usecase.FilterProductsUseCase
import com.example.myapp.domain.usecase.GetCategoriesUseCase
import com.example.myapp.domain.usecase.GetProductsUseCase
import com.example.myapp.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val filterProductsUseCase: FilterProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseViewModel<FeedState, FeedEvent>(FeedState()) {

    init {
        onEvent(FeedEvent.LoadProducts)
    }

    override fun onEvent(event: FeedEvent) {
        when (event) {
            is FeedEvent.LoadProducts -> loadProducts()
            is FeedEvent.Refresh -> refreshProducts()
            is FeedEvent.SelectCategory -> selectCategory(event.category)
            is FeedEvent.ToggleFavorite -> toggleFavorite(event.productId)
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val categories = getCategoriesUseCase(resource.data)
                        updateState { state ->
                            val filtered = filterProductsUseCase(resource.data, state.selectedCategory)
                            state.copy(
                                products = resource.data,
                                filteredProducts = filtered,
                                categories = categories,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        updateState { state ->
                            val filtered = filterProductsUseCase(state.products, state.selectedCategory)
                            state.copy(
                                isLoading = false, 
                                errorMessage = resource.message,
                                products = state.products,
                                filteredProducts = filtered
                            ) 
                        }
                    }
                    is Resource.Loader -> {
                        updateState { it.copy(isLoading = resource.isLoading) }
                    }
                }
            }
        }
    }

    private fun refreshProducts() {
        updateState { it.copy(errorMessage = null) }
        loadProducts()
    }

    private fun selectCategory(category: String?) {
        updateState { state ->
            val filtered = filterProductsUseCase(state.products, category)
            state.copy(
                selectedCategory = category,
                filteredProducts = filtered
            )
        }
    }

    private fun toggleFavorite(productId: Int) {
        updateState { state ->
            val newFavorites = if (state.favoriteProductIds.contains(productId)) {
                state.favoriteProductIds - productId
            } else {
                state.favoriteProductIds + productId
            }
            state.copy(favoriteProductIds = newFavorites)
        }
    }
}
