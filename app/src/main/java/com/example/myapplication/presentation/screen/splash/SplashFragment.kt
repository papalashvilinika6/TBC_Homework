package com.example.myapplication.presentation.screen.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSplashBinding
import com.example.myapplication.presentation.screen.common.BaseFragment
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        SplashSideEffect.NavigateToHome ->
                            findNavController()
                                .navigate(R.id.action_splashFragment2_to_main_nav_graph)

                        SplashSideEffect.NavigateToLogin ->
                            findNavController()
                                .navigate(R.id.action_splashFragment2_to_auth_nav_graph)
                    }
                }
            }
        }
    }
}
