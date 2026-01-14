package com.example.myapp.presentation.screen.image_upload

import android.net.Uri

sealed class ImageUploadEvent {
    data class ImageChosen(val uri: Uri) : ImageUploadEvent()
    object UploadClicked : ImageUploadEvent()
    object ErrorShown : ImageUploadEvent()
}
