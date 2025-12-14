package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Driver
import com.example.myapplication.domain.repository.DriversRepository
import javax.inject.Inject

class GetDriversUseCase @Inject constructor(
    private val repository: DriversRepository
) {
    suspend operator fun invoke(): List<Driver> =
        repository.getDrivers()
}
