package com.example.myapplication.data.remote

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.dto.StoryDto
import retrofit2.Response
import retrofit2.http.GET

interface StoryApi {
    @GET("d3f7ef2c-897b-4b2e-b700-d1b288470965")
    suspend fun getStories(): Response<List<StoryDto>>
}