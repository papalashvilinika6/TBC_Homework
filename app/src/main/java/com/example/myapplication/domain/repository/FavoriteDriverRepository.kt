package com.example.myapplication.domain.repository

interface FavoriteDriverRepository {
    suspend fun saveFavoriteDriver(userId: String, driverId: Int): Result<Unit>
    suspend fun getFavoriteDriver(userId: String): Result<Int?>
    suspend fun removeFavoriteDriver(userId: String): Result<Unit>
}

