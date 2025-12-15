package com.example.myapplication.data.remote

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {
    @GET("1e3f40b1-19a5-4986-ad60-fdc80c27234b")
    suspend fun getPosts(): Response<List<PostDto>>
}