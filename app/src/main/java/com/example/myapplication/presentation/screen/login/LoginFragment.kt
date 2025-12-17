package com.example.myapplication.presentation.screen.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.presentation.screen.common.BaseFragment
import com.example.myapplication.presentation.utils.AuthValidation
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
    }

    private fun setListeners() {
        binding.loginButton.setOnClickListener {

            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (!AuthValidation.validateEmailAndPassword(
                    rootView = binding.root,
                    email = email,
                    password = password
                )
            ) {
                return@setOnClickListener
            }

            viewModel.onEvent(
                LoginEvent.SignInWithEmail(
                    email = email,
                    password = password
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
