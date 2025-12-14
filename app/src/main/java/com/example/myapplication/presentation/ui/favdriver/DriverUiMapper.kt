package com.example.myapplication.presentation.ui.favdriver

import com.example.myapplication.domain.model.Driver
import javax.inject.Inject

class DriverUiMapper @Inject constructor() {

    fun map(driver: Driver): DriverUiModel =
        DriverUiModel(
            id = driver.id,
            fullName = "${driver.firstName} ${driver.lastName}",
            photoUrl = driver.photoUrl,
            backgroundColor = driver.teamColor
        )

    fun mapList(list: List<Driver>): List<DriverUiModel> =
        list.map(::map)
}
