package com.example.myapp.presentation.screen.image_upload

import androidx.lifecycle.viewModelScope
import com.example.myapp.domain.model.UploadStatus
import com.example.myapp.domain.usecase.CompressImageUseCase
import com.example.myapp.domain.usecase.UploadImageUseCase
import com.example.myapp.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageUploadViewModel @Inject constructor(
    private val compressImage: CompressImageUseCase,
    private val uploadImage: UploadImageUseCase
) : BaseViewModel<ImageUploadState, ImageUploadEvent>(ImageUploadState()) {

    private var compressedFilePath: String? = null

    override fun onEvent(event: ImageUploadEvent) {
        when (event) {
            is ImageUploadEvent.ImageChosen -> onImageChosen(event.uri)
            ImageUploadEvent.UploadClicked -> onUpload()
            ImageUploadEvent.ErrorShown -> updateState { it.copy(error = null) }
        }
    }

    private fun onImageChosen(uri: android.net.Uri) {
        updateState { it.copy(loading = true, error = null) }

        viewModelScope.launch {
            try {
                compressedFilePath = compressImage(uri, quality = 80)
                updateState { it.copy(previewUri = uri, hasImage = true, loading = false) }
            } catch (e: Exception) {
                updateState { it.copy(loading = false, error = e.message ?: "Compress failed") }
            }
        }
    }

    private fun onUpload() {
        val path = compressedFilePath
        if (path == null) {
            updateState { it.copy(error = "Select image first") }
            return
        }

        updateState { it.copy(loading = true, error = null) }

        viewModelScope.launch {
            uploadImage(path).collectLatest { status ->
                when (status) {
                    UploadStatus.Enqueued,
                    UploadStatus.Uploading -> updateState { it.copy(loading = true) }

                    UploadStatus.Success -> updateState { it.copy(loading = false, error = null) }

                    is UploadStatus.Error -> updateState { it.copy(loading = false, error = status.message) }

                    UploadStatus.Idle -> updateState { it.copy(loading = false) }
                }
            }
        }
    }
}
