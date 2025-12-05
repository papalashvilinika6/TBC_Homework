package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Card
import retrofit2.Response

interface CardRepository {
    suspend fun getCards(): Response<List<Card>>
}


