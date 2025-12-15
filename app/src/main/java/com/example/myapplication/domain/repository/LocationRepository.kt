package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Place
import kotlinx.coroutines.flow.Flow


interface LocationRepository {
    fun observePlaces(): Flow<List<Place>>
    suspend fun refresh(): Result<Unit>
}

