package com.example.myapplication.view

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.R
import kotlinx.coroutines.launch
import com.example.myapplication.utils.showSnackbar

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()

    override fun listeners() {
        btnLogin()
    }

    override fun observers() {
        observeLoginResult()
    }

    private fun observeLoginResult() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResult.collect { result ->
                    result?.onSuccess {
                        showSnackbar(binding.root, getString(R.string.successful))
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
}
