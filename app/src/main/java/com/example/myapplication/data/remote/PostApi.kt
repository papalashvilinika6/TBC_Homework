package com.example.myapplication.data.remote

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {
    @GET(BuildConfig.POSTS_ENDPOINT)
    suspend fun getPosts(): Response<List<PostDto>>
}