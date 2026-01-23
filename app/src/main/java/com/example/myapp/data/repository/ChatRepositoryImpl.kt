package com.example.myapp.data.repository

import com.example.myapp.data.remote.api.ChatApi
import com.example.myapp.data.remote.common.HandleResponse
import com.example.myapp.data.remote.mapper.toDomain
import com.example.myapp.domain.model.Chat
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val api: ChatApi
) : ChatRepository{

    override fun getChats(): Flow<Resource<List<Chat>>> =
        HandleResponse.safeApiCall { api.getChat() }
            .map { res ->
                when(res) {
                    is Resource.Success -> Resource.Success(res.data.map { it.toDomain() })
                    is Resource.Error -> Resource.Error(res.message)
                    is Resource.Loader -> Resource.Loader(res.isLoading)
                }
            }
}