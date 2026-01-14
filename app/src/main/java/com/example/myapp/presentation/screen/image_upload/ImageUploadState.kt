package com.example.myapp.presentation.screen.image_upload

import android.net.Uri

data class ImageUploadState(
    val previewUri: Uri? = null,
    val loading: Boolean = false,
    val error: String? = null,
    val hasImage: Boolean = false
)

