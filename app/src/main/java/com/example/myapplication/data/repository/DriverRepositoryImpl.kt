package com.example.myapplication.data.repository

import com.example.myapplication.data.mapper.DriverDataMapper
import com.example.myapplication.data.remote.DriversApi
import com.example.myapplication.domain.model.Driver
import com.example.myapplication.domain.repository.DriversRepository
import javax.inject.Inject

class DriversRepositoryImpl @Inject constructor(
    private val api: DriversApi,
    private val mapper: DriverDataMapper
) : DriversRepository {

    override suspend fun getDrivers(): List<Driver> {
        val response = api.getDrivers()
        return mapper.mapList(response.drivers)
    }
}