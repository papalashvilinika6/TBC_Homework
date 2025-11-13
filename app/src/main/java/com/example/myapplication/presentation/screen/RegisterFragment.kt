package com.example.myapplication.presentation.screen

import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.R
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.presentation.viewmodel.UserViewModel
import com.example.myapplication.presentation.utils.showSnackbar
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()


    override fun listeners() {
        btnRegister()
    }

    override fun observers() {
        observeRegisterResult()
        btnEye()
    }

    private fun observeRegisterResult() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerResult.collect { result ->
                    result?.onSuccess {
                        showSnackbar(binding.root, getString(R.string.registration_successful))

                    }
                    result?.onFailure { error ->
                        showSnackbar(binding.root, getString(R.string.registration_failed))
                    }
                }
            }
        }
    }


    private fun btnRegister() = with(binding) {
        btnRegister.setOnClickListener {
            if (!isUsernameValid() || !isEmailValid() || !isPasswordValid()) {
                return@setOnClickListener
            }

            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            viewModel.register(email, username, password)
        }
    }

    private fun isUsernameValid(): Boolean {
        val username = binding.etUsername.text.toString().trim()
        return if (username.isEmpty()) {
            showSnackbar(binding.root, getString(R.string.username_empty))
            false
        } else true
    }

    private fun isEmailValid(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        return if (email.isEmpty()) {
            showSnackbar(binding.root, getString(R.string.email_empty))
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