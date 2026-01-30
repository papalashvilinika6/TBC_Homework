package com.example.myapp.data.remote.service

import com.example.myapp.data.remote.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {
    @GET(POST)
    suspend fun getPosts(): Response<List<PostDto>>


    companion object {
        const val POST = "post"
    }
}
