package com.example.myapplication.presentation.screen

import TokenManager
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.presentation.viewmodel.UserViewModel
import com.example.myapplication.presentation.utils.showSnackbar
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var tokenManager: TokenManager

    override fun listeners() {
        btnLogin()
        btnEye()
    }

    override fun observers() {
        observeLoginResult()
    }

    private fun observeLoginResult() {
        val token = "user_token_from_api"

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResult.collect { result ->
                    result?.onSuccess {
                        showSnackbar(binding.root, getString(R.string.successful))
                        tokenManager.saveToken(token)
                        findNavController().navigate(R.id.action_loginFragment_to_loggedInFragment)
                    }
                    result?.onFailure { error ->
                        showSnackbar(binding.root, getString(R.string.failed))
                    }
                }
            }
        }
    }

    private fun btnLogin() = with(binding) {
        btnLogin.setOnClickListener {
            if (!isUsernameValid() || !isPasswordValid()) {
                return@setOnClickListener
            }

            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            viewModel.login(username, password)
        }
    }

    private fun isUsernameValid(): Boolean {
        val username = binding.etUsername.text.toString().trim()
        return if (username.isEmpty()) {
            showSnackbar(binding.root, getString(R.string.username_empty))
            false
        } else true
    }

    private fun isPasswordValid(): Boolean {
        val password = binding.etPassword.text.toString().trim()
        return if (password.isEmpty()) {
            showSnackbar(binding.root, getString(R.string.password_empty))
            false
        } else true
    }

    fun btnEye() = with(binding) {
        togglePasswordButton.setOnClickListener {
            if (etPassword.transformationMethod is PasswordTransformationMethod) {
                etPassword.transformationMethod = null
                togglePasswordButton.setImageResource(R.drawable.ic_eye)
            } else {
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                togglePasswordButton.setImageResource(R.drawable.eye_off)
            }
            etPassword.setSelection(etPassword.text?.length ?: 0)
        }

    }
}