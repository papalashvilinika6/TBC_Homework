package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.AuthRepository
import javax.inject.Inject


class SignUpWithEmailUseCase @Inject constructor (private val repository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        phone: String?
    ): User {
        return repository.signUpWithEmail(email, password, name, phone)
    }
}