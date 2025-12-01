package com.example.myapplication.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthLocalRepository {
    fun getToken(): Flow<String>
    fun isRemembered(): Flow<Boolean>

    suspend fun saveToken(token: String)
    suspend fun saveRememberMe(remember: Boolean)
}