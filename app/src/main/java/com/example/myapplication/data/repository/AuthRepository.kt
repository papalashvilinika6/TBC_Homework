package com.example.myapplication.data.repository

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.dto.LoginRequestDto
import com.example.myapplication.data.dto.LoginResponseDto
import com.example.myapplication.data.dto.RegisterRequestDto
import com.example.myapplication.data.dto.RegisterResponseDto
import com.example.myapplication.data.network.LoginApi
import com.example.myapplication.data.network.RegisterApi
import com.example.myapplication.data.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val loginApi: LoginApi,
    private val registerApi: RegisterApi,
) {
    fun login(email: String, password: String)
            : Flow<Resource<LoginResponseDto>> =
        HandleResponse.safeApiCall {
            loginApi.login(LoginRequestDto(email, password))
        }


    fun register(email: String, password: String): Flow<Resource<RegisterResponseDto>> =
        HandleResponse.safeApiCall {
            registerApi.register(RegisterRequestDto(email, password))
        }
}