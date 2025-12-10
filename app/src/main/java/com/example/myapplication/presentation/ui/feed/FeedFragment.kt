package com.example.myapplication.presentation.ui.feed

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.FragmentFeedBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.example.myapplication.presentation.ui.feed.adapter.FeedAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment :
    BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::inflate) {

    private val viewModel: FeedViewModel by viewModels()
    private val adapter = FeedAdapter()

    override fun bind() {
        setupRecycler()
        setupListeners()
    }

    override fun observers() {
        observeState()
        observeSideEffects()
    }

    private fun setupRecycler() {
        binding.rvFeed.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFeed.adapter = adapter
    }

    private fun setupListeners() {
        viewModel.onEvent(FeedEvent.LoadFeed)
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.progressFeed.isVisible = state.isLoading
                binding.noInternetBanner.isVisible = !state.isOnline
                adapter.submitList(state.items)
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    is FeedSideEffect.ShowError ->
                        Snackbar.make(binding.root, effect.message, Snackbar.LENGTH_SHORT).show()

                    FeedSideEffect.NoInternetBanner ->
                        binding.noInternetBanner.isVisible = true
                }
            }
        }
    }
}

