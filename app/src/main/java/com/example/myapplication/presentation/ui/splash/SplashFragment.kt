package com.example.myapplication.presentation.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSplashBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun listeners() {}

    override fun bind() {
    }

    override fun observers() {
        observeSplashSide()
    }

    private fun observeSplashSide() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigation.collect { event ->
                    // Check if we're still on splash (if not, deep link already navigated us)
                    if (findNavController().currentDestination?.id != R.id.splashFragment) {
                        return@collect
                    }
                    
                    when (event) {
                        SplashSideEffect.NavigateToHome ->
                            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

                        SplashSideEffect.NavigateToLogin ->
                            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }
                }
            }
        }
    }


}
