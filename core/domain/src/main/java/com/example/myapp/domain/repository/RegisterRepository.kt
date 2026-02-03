package com.example.myapp.domain.repository

import com.example.myapp.domain.model.RegisterField
import com.example.myapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun getRegisterConfig(): Flow<Resource<List<List<RegisterField>>>>
}
