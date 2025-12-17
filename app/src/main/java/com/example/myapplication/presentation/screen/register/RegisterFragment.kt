package com.example.myapplication.presentation.screen.register

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.presentation.screen.common.BaseFragment
import com.example.myapplication.presentation.utils.AuthValidation
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
        viewModel.onEvent(RegisterEvent.CheckSession)
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

    private fun setListeners() = with(binding) {

        createAccountButton.setOnClickListener {

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val name = nameEditText.text.toString().trim()

            if (name.isBlank()) {
                return@setOnClickListener
            }

            if (!AuthValidation.validateEmailAndPassword(
                    rootView = root,
                    email = email,
                    password = password
                )
            ) {
                return@setOnClickListener
            }

            viewModel.onEvent(
                RegisterEvent.RegisterWithEmail(
                    email = email,
                    password = password,
                    name = name
                )
            )
        }

        loginText.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun navigateToHome() {
        findNavController()
            .navigate(R.id.action_register_to_favDriver)
    }

}
