package com.example.myapplication.presentation.ui.login

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun listeners() {
        setLoginButtonListener()
        setRegisterButtonListener()
    }

    override fun bind() {
        setEmailPasswordListeners()
        setupFragmentResultListener()
    }

    override fun observers() {
        observeButtonState()
        observeNavigationEvents()
    }

    private fun setLoginButtonListener() = with(binding) {
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val rememberMe = cbRemember.isChecked

            sendData(email)
            viewModel.onEvent(LoginEvent.Login(email, password, rememberMe))
        }
    }

    private fun setRegisterButtonListener() = with(binding) {
        btnGoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setEmailPasswordListeners() = with(binding) {
        etEmail.addTextChangedListener { text ->
            viewModel.onEvent(LoginEvent.OnEmailChanged(text.toString()))
        }
        etPassword.addTextChangedListener { text ->
            viewModel.onEvent(LoginEvent.OnPasswordChanged(text.toString()))
        }
        etEmail.setOnFocusChangeListener { text, hasFocus ->
            if (hasFocus) LoginEvent.OnPasswordChanged(text.toString())
        }
    }

    private fun observeButtonState() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isButtonEnabled.collect { enabled ->
                    btnLogin.isEnabled = enabled
                    btnLogin.alpha = if (enabled) 1f else 0.5f
                }
            }
        }
    }

    private fun observeNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect { event ->
                    when (event) {
                        LoginEvent.Success -> navigateToHome()
                        else -> Unit
                    }
                }
            }
        }
    }


    private fun setupFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString(EMAIL_KEY) ?: ""
            val password = bundle.getString(PASSWORD_KEY) ?: ""
            fillLoginFields(email, password)
        }
    }

    private fun fillLoginFields(email: String, password: String) {
        binding.etEmail.setText(email)
        binding.etPassword.setText(password)
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun sendData(email: String) {
        val bundle = Bundle().apply {
            putString(EMAIL_KEY, email)
        }
        parentFragmentManager.setFragmentResult(LOGIN_KEY, bundle)
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val EMAIL_KEY = "emailKey"
        const val LOGIN_KEY = "loginKey"
        const val PASSWORD_KEY = "passwordKey"
    }
}

