package com.example.myapp.domain.repository

import android.net.Uri
import com.example.myapp.domain.model.UploadStatus
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun compressToCacheFile(uri: Uri, quality: Int = 80): String
    fun enqueueUpload(filePath: String): Flow<UploadStatus>
}
