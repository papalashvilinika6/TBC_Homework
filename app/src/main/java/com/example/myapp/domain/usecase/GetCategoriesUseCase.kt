package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.Product
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor() {

    operator fun invoke(products: List<Product>): List<String> {
        return listOf("All") + products.map { it.category }.distinct()
    }
}
