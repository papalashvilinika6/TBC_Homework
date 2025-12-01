package com.example.myapplication.presentation.ui.register

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun bind() {
        setupListeners()
    }

    override fun listeners() {}

    override fun observers() {
        observeButtonState()
        observeRegisterState()
        observeNavigation()
    }

    private fun setupListeners() = with(binding) {

        etRegEmail.addTextChangedListener {
            viewModel.onEvent(RegisterEvent.OnEmailChanged(it.toString()))
        }

        etRegPassword.addTextChangedListener {
            viewModel.onEvent(RegisterEvent.OnPasswordChanged(it.toString()))
        }

        etRegRepeatPassword.addTextChangedListener {
            viewModel.onEvent(RegisterEvent.OnRepeatPasswordChanged(it.toString()))
        }

        btnRegister.setOnClickListener {
            val email = etRegEmail.text.toString()
            val password = etRegPassword.text.toString()
            viewModel.onEvent(RegisterEvent.Register(email, password))
        }

        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun observeButtonState() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isButtonEnabled.collect { enabled ->
                    btnRegister.isEnabled = enabled
                    btnRegister.alpha = if (enabled) 1f else 0.5f
                }
            }
        }
    }

    private fun observeRegisterState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { result ->
                    when (result) {
                        is Resource.Error ->
                            Snackbar.make(binding.root, result.message, Snackbar.LENGTH_LONG).show()

                        else -> {}
                    }
                }
            }
        }
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect { event ->
                    if (event is RegisterEvent.Success) {
                        sendDataBack()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                }
            }
        }
    }

    private fun sendDataBack() {
        val bundle = Bundle().apply {
            putString(EMAIL_KEY, binding.etRegEmail.text.toString())
            putString(PASSWORD_KEY, binding.etRegPassword.text.toString())
        }
        parentFragmentManager.setFragmentResult(REQUEST_KEY, bundle)
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val EMAIL_KEY = "emailKey"
        const val PASSWORD_KEY = "passwordKey"
    }
}
