package com.example.myapplication.data.repository

import android.util.Log
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
        Log.d("DRIVERS_DEBUG", "API drivers size = ${response.drivers.size}")
        return mapper.mapList(response.drivers)
    }
}