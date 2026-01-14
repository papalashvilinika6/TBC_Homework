package com.example.myapp.data.remote

import androidx.work.*
import com.example.myapp.domain.model.UploadStatus
import com.example.myapp.data.remote.worker.ImageUploadWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UploadWorkDataSource @Inject constructor(
    private val workManager: WorkManager
) {

    fun enqueueUpload(filePath: String): Flow<UploadStatus> {
        val id = UUID.randomUUID().toString()

        val data = workDataOf(
            ImageUploadWorker.KEY_FILE_PATH to filePath,
            ImageUploadWorker.KEY_UPLOAD_ID to id
        )

        val request = OneTimeWorkRequestBuilder<ImageUploadWorker>()
            .setInputData(data)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10, TimeUnit.SECONDS)
            .build()

        workManager.enqueue(request)

        return workManager.getWorkInfoByIdFlow(request.id).map { info ->
            when (info.state) {
                WorkInfo.State.ENQUEUED -> UploadStatus.Enqueued
                WorkInfo.State.RUNNING -> UploadStatus.Uploading
                WorkInfo.State.SUCCEEDED -> UploadStatus.Success
                WorkInfo.State.FAILED -> UploadStatus.Error("Upload failed")
                WorkInfo.State.CANCELLED -> UploadStatus.Error("Upload cancelled")
                WorkInfo.State.BLOCKED -> UploadStatus.Enqueued
            }
        }
    }
}
