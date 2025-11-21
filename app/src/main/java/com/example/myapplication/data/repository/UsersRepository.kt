package com.example.myapplication.data.repository

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.dto.User
import com.example.myapplication.data.dto.UserResponse
import com.example.myapplication.data.network.UsersApi
import com.example.myapplication.data.utils.Resource
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last
import javax.inject.Inject


class UsersRepository @Inject constructor(private val api: UsersApi) {

    suspend fun getUsers(page: Int = 1): List<User> {
        val result = HandleResponse
            .safeApiCall { api.getUsers(page) }
            .filterIsInstance<Resource.Success<UserResponse>>()
            .firstOrNull()

        return result?.data?.data ?: emptyList()
    }

}