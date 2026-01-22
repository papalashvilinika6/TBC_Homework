package com.example.myapp.domain.repository

import com.example.myapp.domain.model.Order
import com.example.myapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(): Flow<Resource<List<Order>>>
}
