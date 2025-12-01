package com.example.myapplication.domain.usecase.local

import com.example.myapplication.domain.repository.AuthLocalRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val repo: AuthLocalRepository
) {
    suspend operator fun invoke(token: String) {
        repo.saveToken(token)
    }
}