package com.example.myapp.domain.repository

import com.example.myapp.domain.model.Tour
import com.example.myapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface TourRepository {
    fun getTours(): Flow<Resource<List<Tour>>>
}