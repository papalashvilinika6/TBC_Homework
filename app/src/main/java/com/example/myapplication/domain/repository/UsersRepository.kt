package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getUsersFromDb(): Flow<List<User>>

    suspend fun fetchUsersFromNetwork(): Boolean
}
