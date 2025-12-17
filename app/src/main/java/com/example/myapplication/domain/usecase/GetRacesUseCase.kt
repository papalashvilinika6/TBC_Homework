package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Race
import com.example.myapplication.domain.repository.RaceRepository
import javax.inject.Inject

class GetRacesUseCase @Inject constructor(
    private val repository: RaceRepository
) {
    suspend operator fun invoke(): List<Race> =
        repository.getRaces()
}
