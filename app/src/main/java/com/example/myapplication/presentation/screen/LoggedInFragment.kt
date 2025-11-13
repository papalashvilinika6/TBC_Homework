package com.example.myapplication.presentation.screen

import TokenManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentLoggedInBinding
import kotlinx.coroutines.launch

class LoggedInFragment : BaseFragment<FragmentLoggedInBinding>(FragmentLoggedInBinding::inflate) {

    private lateinit var tokenManager: TokenManager

    override fun listeners() {
        btnOut()
    }

    private fun btnOut() {
        binding.btnSignout.setOnClickListener {
            lifecycleScope.launch {
                tokenManager.clearToken()
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }
}