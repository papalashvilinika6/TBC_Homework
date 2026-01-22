package com.example.myapp.data.remote.api

import com.example.myapp.data.remote.dto.OrderDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Body

interface OrderApi {
    @GET("orders")
    suspend fun getOrders(): Response<List<OrderDto>>
}

