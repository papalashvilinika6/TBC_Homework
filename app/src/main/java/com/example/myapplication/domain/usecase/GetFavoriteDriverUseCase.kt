package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.FavoriteDriverRepository
import javax.inject.Inject

class GetFavoriteDriverUseCase @Inject constructor(
    private val favoriteDriverRepository: FavoriteDriverRepository
) {
    suspend operator fun invoke(): Result<Int?> =
        favoriteDriverRepository.getFavoriteDriver()
}

