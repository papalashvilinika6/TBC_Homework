package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor (private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.signOut()
    }
}