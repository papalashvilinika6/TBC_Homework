package com.example.myapplication.domain.usecase.local

import com.example.myapplication.domain.repository.AuthLocalRepository
import javax.inject.Inject

class SaveRememberMeUseCase @Inject constructor(
    private val repo: AuthLocalRepository
) {
    suspend operator fun invoke(remember: Boolean) {
        repo.saveRememberMe(remember)
    }
}