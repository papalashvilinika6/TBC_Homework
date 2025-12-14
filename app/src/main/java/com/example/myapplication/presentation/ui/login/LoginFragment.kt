package com.example.myapplication.presentation.ui.login

import android.util.Log
import android.view.View
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun observers() {
        observeState()
    }

    override fun listeners() {
        setListeners()
    }

    override fun bind() {
        viewModel.processIntent(LoginIntent.CheckSession)
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

    private fun setListeners() {
        binding.loginButton.setOnClickListener {
            viewModel.processIntent(
                LoginIntent.SignInWithEmail(
                    email = binding.emailEditText.text.toString().trim(),
                    password = binding.passwordEditText.text.toString()
                )
            )
        }

        binding.signInWithGoogleButton.setOnClickListener {
            lifecycleScope.launch { signInWithGoogle() }
        }

        binding.createAccountText.setOnClickListener {
            findNavController()
                .navigate(R.id.action_login_to_register)
        }
    }

    private fun navigateToHome() {
        findNavController()
            .navigate(R.id.action_login_to_main)
    }

    private suspend fun signInWithGoogle() {
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
            Log.e("LoginFragment", e.message ?: "Google sign-in failed")
            viewModel.processIntent(LoginIntent.SignOut)
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

            viewModel.processIntent(LoginIntent.SignInWithGoogle(token))
        } else {
            viewModel.processIntent(LoginIntent.SignOut)
        }
    }

    private suspend fun clearGoogleSession() {
        try {
            CredentialManager
                .create(requireContext())
                .clearCredentialState(ClearCredentialStateRequest())
        } catch (e: Exception) {
            Log.e("LoginFragment", "Failed to clear Google credentials", e)
        }
    }
}
