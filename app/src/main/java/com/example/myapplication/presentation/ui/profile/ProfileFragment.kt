package com.example.myapplication.presentation.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.example.myapplication.presentation.ui.login.LoginEvent
import com.example.myapplication.presentation.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun listeners() {
        btnLogout()
    }

    override fun bind() {
        setupFragmentResultListener()
    }

    private fun btnLogout() {
        binding.btnLogout.setOnClickListener {
            viewModel.onEvent(LoginEvent.ClearToken)
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun setupFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener(
            "loginKey",
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString("emailKey") ?: ""
            binding.tvProfileEmail.text = email
        }
    }
}