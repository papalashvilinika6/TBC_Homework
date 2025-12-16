package com.example.myapplication.data.mapper

import android.graphics.Color
import com.example.myapplication.data.dto.DriverDto
import com.example.myapplication.domain.model.Driver
import javax.inject.Inject

class DriverDataMapper @Inject constructor() {

    fun map(dto: DriverDto): Driver =
        Driver(
            id = dto.id,
            firstName = dto.firstName,
            lastName = dto.lastName,
            photoUrl = dto.photo,
            teamColor = Color.parseColor(dto.color),
            favDriverImage = dto.favDriverImage
        )

    fun mapList(list: List<DriverDto>): List<Driver> =
        list.map(::map)
}

