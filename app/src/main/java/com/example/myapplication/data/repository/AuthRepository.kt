package com.example.myapplication.data.repository

import com.example.myapplication.data.common.safeApiCall
import com.example.myapplication.data.datastore.DataStoreManager
import com.example.myapplication.data.dto.LoginRequestDto
import com.example.myapplication.data.dto.LoginResponseDto
import com.example.myapplication.data.dto.RegisterRequestDto
import com.example.myapplication.data.dto.RegisterResponseDto
import com.example.myapplication.data.network.LoginApi
import com.example.myapplication.data.network.RegisterApi
import com.example.myapplication.data.utils.Resource


class AuthRepository(
    private val loginApi: LoginApi,
    private val dataStore: DataStoreManager,
    private val registerApi: RegisterApi
) {

    suspend fun login(email: String, password: String, rememberMe: Boolean): Resource<LoginResponseDto> {
        val result = safeApiCall {
            loginApi.login(LoginRequestDto(email, password))
        }

        if (result is Resource.Success) {
            dataStore.saveToken(result.data?.token ?: "")
            dataStore.saveRememberMe(rememberMe)
        }

        return result
    }

    suspend fun register(email: String, password: String): Resource<RegisterResponseDto> {
        return safeApiCall {
            registerApi.register(RegisterRequestDto(email, password))
        }
    }



        suspend fun getToken(): String? = dataStore.getToken()
    suspend fun clearToken() = dataStore.clearToken()
    suspend fun getRememberMe(): Boolean = dataStore.getRememberMe()
}

