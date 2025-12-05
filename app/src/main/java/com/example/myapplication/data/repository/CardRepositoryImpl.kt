package com.example.myapplication.data.repository

import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.network.CardApi
import com.example.myapplication.domain.model.Card
import com.example.myapplication.domain.repository.CardRepository
import retrofit2.Response
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val api: CardApi
) : CardRepository {

    override suspend fun getCards(): Response<List<Card>> {
        val response = api.getCards()

        return if (response.isSuccessful) {
            val body = response.body()?.map { it.toDomain() } ?: emptyList()
            Response.success(body)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}
