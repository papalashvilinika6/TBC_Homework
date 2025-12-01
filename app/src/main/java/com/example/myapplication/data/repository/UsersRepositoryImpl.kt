package com.example.myapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.data.network.UsersApi
import com.example.myapplication.data.paging.UsersPagingSource
import com.example.myapplication.domain.model.GetUsers
import com.example.myapplication.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import androidx.paging.map
import com.example.myapplication.data.mapper.toDomain

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi
) : UsersRepository {

    override fun getUsersPaging(): Flow<PagingData<GetUsers>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingSource(api) }
        ).flow.map { pagingData ->
            pagingData.map { dto ->
                dto.toDomain()
            }
        }
    }
}
