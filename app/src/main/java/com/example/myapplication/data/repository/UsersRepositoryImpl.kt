package com.example.myapplication.data.repository

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.local.UsersDao
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.mapper.toEntity
import com.example.myapplication.data.network.UsersApi
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.UsersRepository
import com.example.myapplication.data.utils.NetworkChecker
import com.example.myapplication.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi,
    private val dao: UsersDao,
    private val networkChecker: NetworkChecker
) : UsersRepository {

    override fun getUsersFromDb(): Flow<List<User>> =
        dao.getUsers().map { list -> list.map { it.toDomain() } }

    override suspend fun fetchUsersFromNetwork(): Boolean {

        if (!networkChecker.isOnline()) return false

        HandleResponse.safeApiCall {
            api.getUsers()
        }.collect { resource ->
            if (resource is Resource.Success) {
                dao.insertUsers(resource.data.map { it.toEntity() })
            }
        }

        return true
    }
}

