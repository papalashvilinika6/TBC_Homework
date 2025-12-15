package com.example.myapplication.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSplashBinding
import com.example.myapplication.presentation.ui.splash.SplashViewModel
import com.example.myapplication.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    val viewModel : SplashViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.onEvent(SplashEvent.OnStartSplash)
    }

    override fun observers() {
        super.observers()
        observeSplashSideEffect()
    }

    private fun observeSplashSideEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect.collect { event ->
                when(event) {
                    SplashSideEffect.NavigateToMap -> findNavController().navigate(R.id.action_splashFragment_to_permissionFragment)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onEvent(SplashEvent.OnStopSplash)
    }
}