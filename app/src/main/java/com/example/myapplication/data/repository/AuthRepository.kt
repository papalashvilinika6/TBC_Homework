package com.example.myapplication.data.repository

import com.example.myapplication.data.datastore.DataStoreManager
import com.example.myapplication.data.dto.LoginRequestDto
import com.example.myapplication.data.dto.LoginResponseDto
import com.example.myapplication.data.network.LoginApi
import retrofit2.Response

class AuthRepository(
    private val loginApi: LoginApi,
    private val dataStore: DataStoreManager
) {

    suspend fun login(email: String, password: String,rememberMe: Boolean): LoginResponseDto {
        val response: Response<LoginResponseDto> = loginApi.login(LoginRequestDto(email, password))
        if (!response.isSuccessful) throw Exception("Login failed: ${response.errorBody()?.string()}")
        val body = response.body() ?: throw Exception("Response body is null")
        dataStore.saveToken(body.token)
        dataStore.saveRememberMe(rememberMe)
        return body
    }

    suspend fun getToken(): String? = dataStore.getToken()
    suspend fun clearToken() = dataStore.clearToken()
    suspend fun getRememberMe(): Boolean = dataStore.getRememberMe()
}

