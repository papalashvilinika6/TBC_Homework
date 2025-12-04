package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.UsersRepository
import javax.inject.Inject

class FetchUsersUseCase @Inject constructor(
    private val repo: UsersRepository
) {
    suspend operator fun invoke() = repo.fetchUsersFromNetwork()
}
