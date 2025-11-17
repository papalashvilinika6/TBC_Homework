package com.example.myapplication.presentation.ui.login

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.datastore.DataStoreManager
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.example.myapplication.presentation.viewmodel.LoginEvent
import com.example.myapplication.presentation.viewmodel.LoginViewModel
import com.example.myapplication.presentation.viewmodel.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(DataStoreManager(requireContext()))
    }

    override fun listeners() {
        setLoginButtonListener()
        setRegisterButtonListener()
       // checkAutoLogin() ar imushava
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
            viewModel.login(email, password, rememberMe)
        }
    }

    private fun setRegisterButtonListener() = with(binding) {
        btnGoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setEmailPasswordListeners() = with(binding) {
        etEmail.addTextChangedListener { text ->
            viewModel.onEmailChanged(text.toString())
        }

        etPassword.addTextChangedListener { text ->
            viewModel.onPasswordChanged(text.toString())
        }
    }


    private fun checkAutoLogin() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val shouldNavigate = viewModel.hasSavedToken()
                if (shouldNavigate) {
                    viewModel.emitSuccessNavigation()
                }
            }
        }
    }

    private fun observeButtonState() = with(binding) {
        lifecycleScope.launch {
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
                        is LoginEvent.Success -> navigateToHome()
                        is LoginEvent.Error -> showError(event.message)
                    }
                }
            }
        }
    }

    private fun setupFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener(
            "requestKey",
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString("emailKey") ?: ""
            val password = bundle.getString("passwordKey") ?: ""
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

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun sendData(email: String) {
        val bundle = Bundle().apply {
            putString("emailKey", email)
        }
        parentFragmentManager.setFragmentResult("loginKey", bundle)
    }
}


