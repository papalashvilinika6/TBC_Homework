package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.FavoriteDriverRepository
import javax.inject.Inject

class RemoveFavoriteDriverUseCase @Inject constructor(
    private val favoriteDriverRepository: FavoriteDriverRepository
) {
    suspend operator fun invoke(userId: String): Result<Unit> =
        favoriteDriverRepository.removeFavoriteDriver(userId)
}

