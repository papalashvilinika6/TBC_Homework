package com.example.myapp.domain.model

sealed class UploadStatus {
    object Idle : UploadStatus()
    object Enqueued : UploadStatus()
    object Uploading : UploadStatus()
    object Success : UploadStatus()
    data class Error(val message: String) : UploadStatus()
}