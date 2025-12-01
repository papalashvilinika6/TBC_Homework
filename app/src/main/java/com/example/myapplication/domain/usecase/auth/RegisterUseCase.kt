package com.example.myapplication.domain.usecase.auth

import com.example.myapplication.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String) =
        repository.register(email, password)
}