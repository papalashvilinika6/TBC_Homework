package com.example.myapp.data.remote.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.storage.FirebaseStorage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.UUID

@HiltWorker
class ImageUploadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val firebaseStorage: FirebaseStorage
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val filePath = inputData.getString(KEY_FILE_PATH) ?: return Result.failure()

        val file = File(filePath)
        if (!file.exists()) return Result.failure()

        return try {
            val bytes = file.readBytes()
            val path = "images/${UUID.randomUUID()}.jpg"

            firebaseStorage.reference.child(path)
                .putBytes(bytes)
                .await()

            file.delete()

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val KEY_FILE_PATH = "file_path"
        const val KEY_UPLOAD_ID = "upload_id"
    }
}
