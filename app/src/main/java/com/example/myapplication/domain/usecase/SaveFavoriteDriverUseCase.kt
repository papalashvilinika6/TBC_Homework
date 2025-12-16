package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.FavoriteDriverRepository
import javax.inject.Inject

class SaveFavoriteDriverUseCase @Inject constructor(
    private val favoriteDriverRepository: FavoriteDriverRepository
) {
    suspend operator fun invoke(userId: String, driverId: Int): Result<Unit> =
        favoriteDriverRepository.saveFavoriteDriver(userId, driverId)
}

