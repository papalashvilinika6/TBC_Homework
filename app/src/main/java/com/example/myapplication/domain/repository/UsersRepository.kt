package com.example.myapplication.domain.repository

import androidx.paging.PagingData
import com.example.myapplication.domain.model.GetUsers
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsersPaging(): Flow<PagingData<GetUsers>>
}