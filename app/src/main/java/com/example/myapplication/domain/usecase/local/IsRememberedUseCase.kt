package com.example.myapplication.domain.usecase.local

import com.example.myapplication.domain.repository.AuthLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsRememberedUseCase @Inject constructor(
    private val repo: AuthLocalRepository
) {
    operator fun invoke(): Flow<Boolean> = repo.isRemembered()
}