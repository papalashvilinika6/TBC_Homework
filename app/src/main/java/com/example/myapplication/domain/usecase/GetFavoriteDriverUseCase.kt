package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.FavoriteDriverRepository
import javax.inject.Inject

class GetFavoriteDriverUseCase @Inject constructor(
    private val repository: FavoriteDriverRepository
) {
    suspend operator fun invoke(): Int? {
        return repository.getFavoriteDriverId()
    }
}

