package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.RegisterField
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRegisterConfigUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    operator fun invoke(): Flow<Resource<List<List<RegisterField>>>> {
        return repository.getRegisterConfig()
    }
}
