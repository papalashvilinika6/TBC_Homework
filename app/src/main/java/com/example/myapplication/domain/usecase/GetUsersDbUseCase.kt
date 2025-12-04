package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.UsersRepository
import javax.inject.Inject

class GetUsersDbUseCase @Inject constructor(
    private val repo: UsersRepository
) {
    operator fun invoke() = repo.getUsersFromDb()
}
