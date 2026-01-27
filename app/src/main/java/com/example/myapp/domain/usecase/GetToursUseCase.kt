package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.Tour
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.TourRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetToursUseCase @Inject constructor(
    private val rep: TourRepository
) {
    operator fun invoke(): Flow<Resource<List<Tour>>> {
        return rep.getTours()
    }
}