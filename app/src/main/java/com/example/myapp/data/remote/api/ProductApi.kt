package com.example.myapp.data.remote.api

import com.example.myapp.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

    @GET("events")
    suspend fun getProduct() : Response<List<ProductDto>>
}