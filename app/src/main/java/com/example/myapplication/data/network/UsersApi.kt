package com.example.myapplication.data.network

import com.example.myapplication.data.remote.UserDto
import retrofit2.Response
import retrofit2.http.GET


interface UsersApi {
    @GET("v1/3668d139-e182-4fe2-b909-6259524117cb")
    suspend fun getUsers(): Response<List<UserDto>>
}
