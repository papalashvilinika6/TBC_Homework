package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor (private val repository: AuthRepository) {
    suspend operator fun invoke(idToken: String): User {
        return repository.signInWithGoogle(idToken)
    }
}