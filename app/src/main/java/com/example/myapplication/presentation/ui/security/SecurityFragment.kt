package com.example.myapplication.presentation.ui.security

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSecurityBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.example.myapplication.presentation.ui.security.keypad.KeypadAdapter
import com.example.myapplication.presentation.ui.security.keypad.KeypadItem
import com.example.myapplication.presentation.ui.security.pin.PinEvent
import com.example.myapplication.presentation.ui.security.pin.PinSideEffect
import com.example.myapplication.presentation.utils.shake
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecurityFragment : BaseFragment<FragmentSecurityBinding>(FragmentSecurityBinding::inflate) {
    override fun listeners() {
        setupRecycler()
        observeState()
        observeSideEffects()
    }


    private val viewModel: SecurityViewModel by viewModels()
    private lateinit var adapter: KeypadAdapter


        private fun setupRecycler() {
            adapter = KeypadAdapter { item ->
                when (item) {
                    is KeypadItem.Number -> viewModel.onEvent(PinEvent.NumberPressed(item.value))
                    KeypadItem.Delete -> viewModel.onEvent(PinEvent.DeletePressed)
                    KeypadItem.Fingerprint -> viewModel.onEvent(PinEvent.FingerprintPressed)
                }
            }

            binding.rvKeypad.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.rvKeypad.adapter = adapter
        }

        private fun observeState() {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.state.collect { state ->
                    updateDots(state.pin.size)
                }
            }
        }

        private fun observeSideEffects() {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        PinSideEffect.Success -> showSuccess()
                        PinSideEffect.Error -> showError()
                    }
                }
            }
        }

        private fun updateDots(count: Int) = with(binding) {
            val dots = listOf(dot1, dot2, dot3, dot4)

            dots.forEachIndexed { index, img ->
                img.setImageResource(
                    if (index < count) R.drawable.dot_active else R.drawable.dot_inactive
                )
            }
        }

        private fun showSuccess() {
            Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
        }

        private fun showError() = with(binding) {
            Snackbar.make(binding.root, "Incorrect Password", Snackbar.LENGTH_SHORT).show()

            dot1.shake()
            dot2.shake()
            dot3.shake()
            dot4.shake()
        }

}