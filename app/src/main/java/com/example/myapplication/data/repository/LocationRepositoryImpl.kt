package com.example.myapplication.data.repository

import com.example.myapplication.data.local.PlaceDao
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.mapper.toEntity
import com.example.myapplication.domain.model.Place
import com.example.myapplication.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val api: ApiService,
    private val dao: PlaceDao,
    private val io: CoroutineDispatcher
) : LocationRepository {

    override fun observePlaces(): Flow<List<Place>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun refresh(): Result<Unit> = withContext(io) {
        runCatching {
            val remote = api.getPlaces()
            dao.clear()
            dao.upsertAll(remote.map { it.toEntity() })
        }.map { }
    }
}

