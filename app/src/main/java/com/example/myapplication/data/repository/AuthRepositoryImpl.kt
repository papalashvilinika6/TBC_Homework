package com.example.myapplication.data.repository

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.dto.LoginRequestDto
import com.example.myapplication.data.dto.RegisterRequestDto
import com.example.myapplication.data.mapper.asResource
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.network.LoginApi
import com.example.myapplication.data.network.RegisterApi
import com.example.myapplication.domain.model.LoginResult
import com.example.myapplication.domain.model.RegisterResult
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val registerApi: RegisterApi
) : AuthRepository {

    override fun login(email: String, password: String)
            : Flow<Resource<LoginResult>> {

        return HandleResponse.safeApiCall {
            loginApi.login(LoginRequestDto(email, password))
        }.asResource { dto ->
            dto.toDomain()
        }
    }

    override fun register(email: String, password: String)
            : Flow<Resource<RegisterResult>> {

        return HandleResponse.safeApiCall {
            registerApi.register(RegisterRequestDto(email, password))
        }.asResource { dto ->
            dto.toDomain()
        }
    }
}
