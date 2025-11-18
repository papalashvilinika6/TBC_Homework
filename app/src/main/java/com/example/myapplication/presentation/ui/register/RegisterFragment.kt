package com.example.myapplication.presentation.ui.register

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.utils.utils
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.presentation.ui.register.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding> (FragmentRegisterBinding::inflate) {

    val viewModel : RegisterViewModel by viewModels()

    override fun listeners() {
        btnRegister()
        btnBack()
    }

    override fun observers() {
        observeLoginState()
    }

    fun btnRegister() = with(binding) {
        btnRegister.setOnClickListener {
            val email = etRegEmail.text.toString()
            val password = etRegPassword.text.toString()
            val repeatPassword = etRegRepeatPassword.text.toString()


            if(!validateInputs(email, password)) return@setOnClickListener
            if(!repeatPassword(password, repeatPassword)) return@setOnClickListener

            viewModel.onEvent(RegisterEvent.Register(email, password))
        }
    }

    fun btnBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun observeLoginState() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerResult.collect { result ->
                    result?.let {
                        if (it.isSuccess) {
                            sendData(etRegEmail.text.toString(), etRegPassword.text.toString())
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }

                        if (it.isFailure) {
                            Snackbar.make(binding.root, "Login failed", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        return if (!utils.isEmailValid(email) || !utils.isPasswordValid(password)) {
            Snackbar.make(binding.root, "Invalid Inputs", Snackbar.LENGTH_LONG).show()
            return false
        } else true
    }

    private fun repeatPassword(password: String, repeatPassword: String) : Boolean {
        return if (password != repeatPassword) {
            Snackbar.make(binding.root, "Passwords do not match", Snackbar.LENGTH_LONG).show()
            false
        } else true
    }

    private fun sendData(email: String, password: String) {
        val bundle = Bundle().apply {
            putString("emailKey", email)
            putString("passwordKey", password)
        }
        parentFragmentManager.setFragmentResult("requestKey", bundle)
    }

}