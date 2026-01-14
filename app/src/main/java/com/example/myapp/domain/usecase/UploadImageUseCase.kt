package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.UploadStatus
import com.example.myapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repo: ImageRepository
) {
    operator fun invoke(filePath: String): Flow<UploadStatus> {
        return repo.enqueueUpload(filePath)
    }
}
