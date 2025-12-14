package com.example.myapplication.presentation.ui.favdriver

import android.graphics.Color
import com.example.myapplication.data.dto.DriverDto
import javax.inject.Inject

class DriverMapper @Inject constructor() {

    fun map(dto: DriverDto): DriverUiModel {
        return DriverUiModel(
            id = dto.id,
            fullName = "${dto.firstName} ${dto.lastName}",
            photoUrl = dto.photo,
            backgroundColor = Color.parseColor(dto.color)
        )
    }

    fun mapList(list: List<DriverDto>): List<DriverUiModel> =
        list.map { map(it) }
}
