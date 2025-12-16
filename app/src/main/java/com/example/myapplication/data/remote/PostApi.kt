package com.example.myapplication.data.remote

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {
    @GET("acf19b09-c95c-41fb-aead-7db34733714a")
    suspend fun getPosts(): Response<List<PostDto>>
}