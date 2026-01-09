package com.example.challenge.presentation.screen.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.challenge.databinding.FragmentSplashBinding
import com.example.challenge.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel: SplashViewModel by viewModels()

    override fun bind() {

    }

    override fun bindViewActionListeners() {

    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }


    }

    private fun handleNavigationEvents(event: SplashUiEvent) {
        when (event) {
            is SplashUiEvent.NavigateToConnections -> findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToFriendsFragment()
            )

            is SplashUiEvent.NavigateToLogIn -> findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToLogInFragment()
            )
        }
    }
}
