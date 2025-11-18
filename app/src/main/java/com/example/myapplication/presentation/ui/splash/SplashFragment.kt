package com.example.myapplication.presentation.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSplashBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    val viewModel : SplashViewModel by viewModels()

    override fun listeners() {

    }

    override fun bind() {
        viewModel.onEvent(SplashEvent.OnStartSplash)
    }

    override fun observers() {
        observeSplashSide()
    }

    fun observeSplashSide() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { it ->
                    when(it) {
                        SplashSideEffect.NavigateToHome -> findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                        SplashSideEffect.NavigateToLogin ->   findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }

                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onEvent(SplashEvent.OnStopSplash)
    }
}