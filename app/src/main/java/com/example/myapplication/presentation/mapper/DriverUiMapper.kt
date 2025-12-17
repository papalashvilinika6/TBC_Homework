package com.example.myapplication.presentation.mapper

import com.example.myapplication.domain.model.Driver
import com.example.myapplication.presentation.model.DriverUiModel
import javax.inject.Inject

class DriverUiMapper @Inject constructor() {

    fun map(driver: Driver): DriverUiModel =
        DriverUiModel(
            id = driver.id,
            fullName = "${driver.firstName} ${driver.lastName}",
            photoUrl = driver.photoUrl,
            backgroundColor = driver.teamColor,
            favDriverImage = driver.favDriverImage
        )

    fun mapList(list: List<Driver>): List<DriverUiModel> =
        list.map(::map)
}