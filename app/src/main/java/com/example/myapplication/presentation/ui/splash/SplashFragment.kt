package com.example.myapplication.presentation.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSplashBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSideEffects()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onEvent(SplashEvent.OnStartSplash)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onEvent(SplashEvent.OnStopSplash)
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    SplashSideEffect.NavigateToHome ->
                        findNavController()
                            .navigate(R.id.action_splash_to_main)

                    SplashSideEffect.NavigateToLogin ->
                        findNavController()
                            .navigate(R.id.action_splash_to_login)
                }
            }
        }
    }
}
