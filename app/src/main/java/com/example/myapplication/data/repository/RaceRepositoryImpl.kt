package com.example.myapplication.data.repository

import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.remote.RaceApi
import com.example.myapplication.domain.model.Race
import com.example.myapplication.domain.repository.RaceRepository
import javax.inject.Inject

class RaceRepositoryImpl @Inject constructor(
    private val api: RaceApi
) : RaceRepository {

    override suspend fun getRaces(): List<Race> =
        api.getRaces().map { it.toDomain() }
}
