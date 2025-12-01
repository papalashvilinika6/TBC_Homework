package com.example.myapplication.presentation.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSplashBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    val viewModel : SplashViewModel by viewModels()
    override fun listeners() {}


    override fun bind() {
        viewModel.onEvent(SplashEvent.OnStartSplash)
    }

    override fun observers() {
        observeSplashSide()
    }

    fun observeSplashSide() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigation.collect { event ->
                when(event) {
                    SplashSideEffect.NavigateToHome -> findNavController().navigate(R.id.homeFragment)
                    SplashSideEffect.NavigateToLogin -> findNavController().navigate(R.id.loginFragment)
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        viewModel.onEvent(SplashEvent.OnStopSplash)
    }
}