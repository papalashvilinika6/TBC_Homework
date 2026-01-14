package com.example.myapp.data.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun compressToCacheFile(uri: Uri, quality: Int): String = withContext(Dispatchers.IO) {
        val bitmap = context.contentResolver.openInputStream(uri).use { input ->
            requireNotNull(input) { "Cannot open input stream for uri: $uri" }
            BitmapFactory.decodeStream(input)
        } ?: error("Failed to decode bitmap")

        val outFile = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
        FileOutputStream(outFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
        }
        outFile.absolutePath
    }
}
