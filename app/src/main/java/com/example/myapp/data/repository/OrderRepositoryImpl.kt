package com.example.myapp.data.repository

import com.example.myapp.data.remote.api.OrderApi
import com.example.myapp.data.remote.common.HandleResponse
import com.example.myapp.data.remote.mapper.OrderMapper.toDomain
import com.example.myapp.domain.model.Order
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: OrderApi
) : OrderRepository {

    override fun getOrders(): Flow<Resource<List<Order>>> =
        HandleResponse.safeApiCall {
            api.getOrders()
        }.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    Resource.Success(
                        resource.data.map { it.toDomain() }
                    )
                }

                is Resource.Error -> {
                    Resource.Error(resource.message)
                }

                is Resource.Loader -> {
                    Resource.Loader(resource.isLoading)
                }
            }
        }

}
