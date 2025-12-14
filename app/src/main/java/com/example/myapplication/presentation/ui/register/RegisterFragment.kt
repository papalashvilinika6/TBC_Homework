package com.example.myapplication.presentation.ui.register

import android.util.Log
import android.view.View
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()


    override fun observers() {
        observeState()
    }

    override fun bind() {
        viewModel.processIntent(RegisterIntent.CheckSession)
    }

    override fun listeners() {
        setListeners()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progressBar.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                binding.errorText.apply {
                    text = state.error.orEmpty()
                    visibility = if (state.error != null) View.VISIBLE else View.GONE
                }

                if (state.user != null) {
                    navigateToHome()
                }
            }
        }
    }

    private fun setListeners() = with(binding){
        binding.createAccountButton.setOnClickListener {
            viewModel.processIntent(
                RegisterIntent.RegisterWithEmail(
                    email = emailEditText.text.toString().trim(),
                    password = passwordEditText.text.toString(),
                    name = nameEditText.text.toString().trim(),
                    phone = phoneEditText.text.toString().trim()
                )
            )
        }

        registerWithGoogleButton.setOnClickListener {
            lifecycleScope.launch { registerWithGoogle() }
        }

        loginText.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun navigateToHome() {
        findNavController()
            .navigate(R.id.action_registerFragment_to_favDriverFragment)
    }

    private suspend fun registerWithGoogle() {
        val credentialManager = CredentialManager.create(requireContext())

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(requireContext(), request)
            handleGoogleResult(result.credential)
        } catch (e: GetCredentialException) {
            Log.e("RegisterFragment", e.message ?: "Google sign-up failed")
            viewModel.processIntent(RegisterIntent.SignOut)
        }
    }

    private fun handleGoogleResult(credential: Credential) {
        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val token = GoogleIdTokenCredential
                .createFrom(credential.data)
                .idToken

            viewModel.processIntent(
                RegisterIntent.RegisterWithGoogle(token)
            )
        } else {
            viewModel.processIntent(RegisterIntent.SignOut)
        }
    }
}
