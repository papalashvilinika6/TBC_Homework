package com.example.myapplication.domain.usecase.auth

import com.example.myapplication.domain.repository.AuthLocalRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repo: AuthLocalRepository
) {
    suspend operator fun invoke() {
        println("DEBUG LOGOUT: clearing token")
        repo.saveToken("")
        repo.saveRememberMe(false)
        println("DEBUG LOGOUT: done saving")
    }
}