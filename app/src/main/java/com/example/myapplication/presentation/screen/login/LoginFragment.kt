package com.example.myapplication.presentation.screen.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.presentation.screen.common.BaseFragment
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
        viewModel.onEvent(LoginEvent.CheckSession)
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
            viewModel.onEvent(
                LoginEvent.SignInWithEmail(
                    email = binding.emailEditText.text.toString().trim(),
                    password = binding.passwordEditText.text.toString()
                )
            )
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


}
