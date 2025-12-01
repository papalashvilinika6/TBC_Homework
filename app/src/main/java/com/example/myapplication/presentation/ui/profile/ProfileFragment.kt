package com.example.myapplication.presentation.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun listeners() {
        btnLogout()
    }

    override fun bind() {
        setupFragmentResultListener()
    }

    private fun btnLogout() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun setupFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener(
            LOGIN_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString(EMAIL_KEY) ?: ""
            binding.tvProfileEmail.text = email
        }
    }



    companion object {
        const val EMAIL_KEY = "emailKey"
        const val LOGIN_KEY = "loginKey"
    }
}