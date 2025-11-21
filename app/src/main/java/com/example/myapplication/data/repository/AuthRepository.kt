package com.example.myapplication.data.repository

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.datastore.DataStoreManager
import com.example.myapplication.data.dto.LoginRequestDto
import com.example.myapplication.data.dto.LoginResponseDto
import com.example.myapplication.data.dto.RegisterRequestDto
import com.example.myapplication.data.dto.RegisterResponseDto
import com.example.myapplication.data.network.LoginApi
import com.example.myapplication.data.network.RegisterApi
import com.example.myapplication.data.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val loginApi: LoginApi,
    private val dataStore: DataStoreManager,
    private val registerApi: RegisterApi,
) {
    fun login(email: String, password: String, rememberMe: Boolean)
            : Flow<Resource<LoginResponseDto>> =
        HandleResponse.safeApiCall {
            loginApi.login(LoginRequestDto(email, password))
        }.onEach { result ->
            if (result is Resource.Success) {
                dataStore.saveToken(result.data.token)
                dataStore.saveRememberMe(rememberMe)
            }
        }


    fun register(email: String, password: String): Flow<Resource<RegisterResponseDto>> =
        HandleResponse.safeApiCall {
            registerApi.register(RegisterRequestDto(email, password))
        }.onEach { result ->
            if (result is Resource.Success) {
                dataStore.saveToken(result.data.token)
            }
        }

    suspend fun getToken(): String? = dataStore.getToken()

    suspend fun clearToken() = dataStore.clearToken()

    suspend fun getRememberMe(): Boolean = dataStore.getRememberMe()
}