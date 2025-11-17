package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.User
import com.example.myapplication.data.network.UsersApi

class UsersRepository(private val api: UsersApi) {
    suspend fun getUsers(page: Int = 1): List<User> {
        val response = api.getUsers(page)
        if (!response.isSuccessful) throw Exception("Failed to load users: ${response.errorBody()?.string()}")
        return response.body()?.data ?: emptyList()
    }
}
