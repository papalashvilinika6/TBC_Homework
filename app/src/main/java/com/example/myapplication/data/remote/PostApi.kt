package com.example.myapplication.data.remote

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {
    @GET("742b7a1e-f3f6-479f-8792-01449a072742")
    suspend fun getPosts(): Response<List<PostDto>>
}