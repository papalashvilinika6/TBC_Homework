package com.example.myapplication.data.remote.service

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.remote.dto.CategoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(BuildConfig.ENDPOINT)
    suspend fun getCategories(@Query("search") search: String? = null): Response<List<CategoryDto>>
}