package com.example.myapp.domain.usecase

import android.net.Uri
import com.example.myapp.domain.repository.ImageRepository
import javax.inject.Inject

class CompressImageUseCase @Inject constructor(
    private val repo: ImageRepository
) {
    suspend operator fun invoke(uri: Uri, quality: Int = 80): String {
        return repo.compressToCacheFile(uri, quality)
    }
}
