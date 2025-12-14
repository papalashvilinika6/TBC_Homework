package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor (private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): User {
        return repository.signInWithEmail(email, password)
    }
}