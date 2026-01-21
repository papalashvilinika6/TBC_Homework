package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.Product
import javax.inject.Inject

class FilterProductsUseCase @Inject constructor() {
    
    operator fun invoke(
        products: List<Product>,
        category: String?
    ): List<Product> {
        return if (category == null || category == "All") {
            products
        } else {
            products.filter { it.category.equals(category, ignoreCase = true) }
        }
    }
}
