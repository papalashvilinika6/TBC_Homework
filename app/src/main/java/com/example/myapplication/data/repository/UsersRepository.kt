package com.example.myapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.data.dto.User
import com.example.myapplication.data.network.UsersApi
import com.example.myapplication.data.paging.UsersPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UsersRepository @Inject constructor(private val api: UsersApi) {
    fun getUsersPaging(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingSource(api) }
        ).flow
    }

}