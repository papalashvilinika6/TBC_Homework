package com.example.myapplication.data.remote

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.dto.StoryDto
import retrofit2.Response
import retrofit2.http.GET

interface StoryApi {
    @GET(BuildConfig.STORIES_ENDPOINT)
    suspend fun getStories(): Response<List<StoryDto>>
}