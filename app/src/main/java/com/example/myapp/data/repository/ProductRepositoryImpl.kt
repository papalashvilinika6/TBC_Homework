package com.example.myapp.data.repository

import com.example.myapp.data.remote.api.ProductApi
import com.example.myapp.data.remote.common.HandleResponse
import com.example.myapp.domain.model.Product
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.myapp.data.remote.mapper.toDomain
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {
    override fun getProducts(): Flow<Resource<List<Product>>> =
        HandleResponse.safeApiCall { api.getProduct() }
            .map { res ->
                when (res) {
                    is Resource.Success -> Resource.Success(res.data.map { it.toDomain() })
                    is Resource.Error -> Resource.Error(res.message)
                    is Resource.Loader -> Resource.Loader(res.isLoading)
                }
            }

}
