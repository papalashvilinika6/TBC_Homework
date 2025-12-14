package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Driver

interface DriversRepository {
    suspend fun getDrivers(): List<Driver>
}


