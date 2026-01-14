package com.example.myapp.presentation.screen.image_upload

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapp.R
import com.example.myapp.databinding.FragmentImageUploadBinding
import com.example.myapp.presentation.screen.image_picker.ImagePickerBottomSheet
import com.example.myapplication.presentation.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import com.example.myapp.presentation.extension.showSnackBar

@AndroidEntryPoint
class ImageUploadFragment :
    BaseFragment<FragmentImageUploadBinding>(FragmentImageUploadBinding::inflate) {

    private val vm: ImageUploadViewModel by viewModels()

    private var cameraTempUri: Uri? = null

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) launchCamera() else snack(getString(R.string.camera_permission_denied))
        }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (!success) return@registerForActivityResult
            val uri = cameraTempUri ?: return@registerForActivityResult
            vm.onEvent(ImageUploadEvent.ImageChosen(uri))
        }

    private val pickFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) return@registerForActivityResult
            vm.onEvent(ImageUploadEvent.ImageChosen(uri))
        }

    override fun bind() {
        binding.loaderInclude.root.visibility = android.view.View.GONE
    }

    override fun listeners() {
        binding.btnAddImage.setOnClickListener {
            ImagePickerBottomSheet(
                onTakePicture = { onTakePictureClicked() },
                onChooseGallery = { pickFromGallery.launch(getString(R.string.image)) }
            ).show(childFragmentManager, getString(R.string.picker))
        }

        binding.btnUpload.setOnClickListener {
            vm.onEvent(ImageUploadEvent.UploadClicked)
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect { state ->
                    binding.loaderInclude.root.visibility =
                        if (state.loading) android.view.View.VISIBLE else android.view.View.GONE

                    state.previewUri?.let { binding.imageView.setImageURI(it) }

                    binding.btnUpload.isEnabled = state.hasImage && !state.loading

                    state.error?.let {
                        snack(it)
                        vm.onEvent(ImageUploadEvent.ErrorShown)
                    }
                }
            }
        }
    }

    private fun onTakePictureClicked() {
        val granted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) launchCamera() else requestCameraPermission.launch(Manifest.permission.CAMERA)
    }

    private fun launchCamera() {
        val tmpFile = File(requireContext().cacheDir,
            getString(R.string.camera_jpg, System.currentTimeMillis()))
        cameraTempUri = FileProvider.getUriForFile(
            requireContext(),
            getString(R.string.file_provider, requireContext().packageName),
            tmpFile
        )
        takePicture.launch(cameraTempUri)
    }

    private fun snack(msg: String) {
        binding.root.showSnackBar(msg)
    }
}
