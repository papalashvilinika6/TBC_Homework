package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Race

interface RaceRepository {
    suspend fun getRaces(): List<Race>
}
