package com.example.myapp.domain.repository

import com.example.myapp.domain.model.Product
import com.example.myapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts() : Flow<Resource<List<Product>>>
}