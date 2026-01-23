package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.Chat
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val rep: ChatRepository
) {
    operator fun invoke(): Flow<Resource<List<Chat>>> {
        return rep.getChats()
    }
}