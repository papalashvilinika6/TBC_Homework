package com.example.myapp.data.repository

import com.example.myapp.data.remote.common.HandleResponse
import com.example.myapp.data.remote.mapper.toDomain
import com.example.myapp.data.remote.service.RegisterApi
import com.example.myapp.domain.model.RegisterField
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val api: RegisterApi
) : RegisterRepository {

    override fun getRegisterConfig(): Flow<Resource<List<List<RegisterField>>>> =
        HandleResponse.safeApiCall { api.getRegisterConfig() }
            .map { resource ->
                when (resource) {
                    is Resource.Error -> Resource.Error(resource.message)
                    is Resource.Loader -> Resource.Loader(resource.isLoading)
                    is Resource.Success -> {
                        val mapped: List<List<RegisterField>> =
                            resource.data.map { group ->
                                group.mapNotNull { dto ->
                                    val id = dto.fieldId ?: -1
                                    if (id == -1) null else dto.toDomain()
                                }
                            }

                        Resource.Success(mapped)
                    }
                }
            }
}
