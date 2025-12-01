package com.example.myapplication.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.data.dto.UserResponseDto
import com.example.myapplication.data.network.UsersApi

class UsersPagingSource(
    private val api: UsersApi
) : PagingSource<Int, UserResponseDto.UserDto>() {

    override fun getRefreshKey(state: PagingState<Int, UserResponseDto.UserDto>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponseDto.UserDto> {
        return try {
            val page = params.key ?: 1

            val response = api.getUsers(page)

            if (!response.isSuccessful) {
                return LoadResult.Error(Exception("API error: ${response.code()}"))
            }

            val body = response.body() ?: return LoadResult.Error(Exception("Empty body"))

            val users = body.data
            val totalPages = body.totalPages

            LoadResult.Page(
                data = users,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < totalPages) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

