package com.example.myapplication.presentation.screen.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.presentation.screen.common.BaseFragment
import com.example.myapplication.presentation.utils.showSnack
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(ProfileEvent.LoadProfile)

    }

    override fun listeners() {
        setupListeners()
    }

    override fun observers() {
        observeAcc()
    }

    private fun observeAcc() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.tvName.text = state.name
                    binding.tvNameValue.text = state.name
                    binding.tvEmail.text = state.email
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        ProfileSideEffect.NavigateLogin ->
                            findNavController().setGraph(R.navigation.auth_nav_graph)
                    is ProfileSideEffect.ShowError ->
                        binding.root.showSnack(effect.message, Snackbar.LENGTH_LONG)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.onEvent(ProfileEvent.Logout)
        }

        binding.btnDeleteAccount.setOnClickListener {
            viewModel.onEvent(ProfileEvent.DeleteAccount)
        }
    }
}
