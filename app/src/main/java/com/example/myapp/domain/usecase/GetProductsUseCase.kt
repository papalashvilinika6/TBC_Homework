package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.Product
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    operator fun invoke(): Flow<Resource<List<Product>>> {
        return repository.getProducts()
    }
}
