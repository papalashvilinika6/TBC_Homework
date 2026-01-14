package com.example.myapp.data.repository

import android.net.Uri
import com.example.myapp.data.local.ImageLocalDataSource
import com.example.myapp.data.remote.UploadWorkDataSource
import com.example.myapp.domain.model.UploadStatus
import com.example.myapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val local: ImageLocalDataSource,
    private val remote: UploadWorkDataSource
) : ImageRepository {

    override suspend fun compressToCacheFile(uri: Uri, quality: Int): String {
        return local.compressToCacheFile(uri, quality)
    }

    override fun enqueueUpload(filePath: String): Flow<UploadStatus> {
        return remote.enqueueUpload(filePath)
    }
}
