package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.LoginResult
import com.example.myapplication.domain.model.RegisterResult
import com.example.myapplication.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Resource<LoginResult>>
    fun register(email: String, password: String): Flow<Resource<RegisterResult>>
}
