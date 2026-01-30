package com.example.myapp.data.remote.service

import com.example.myapp.data.remote.dto.PostDto
import com.example.myapp.data.remote.dto.StoryDto
import retrofit2.Response
import retrofit2.http.GET

interface StoryApi {
    @GET(STORY)
    suspend fun getStories(): Response<List<StoryDto>>


    companion object {
        const val STORY = "story"
    }
}