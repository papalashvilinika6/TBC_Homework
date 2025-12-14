package com.example.myapplication.presentation.ui.favdriver

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentFavDriverBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavDriverFragment :
    BaseFragment<FragmentFavDriverBinding>(FragmentFavDriverBinding::inflate) {

    private val viewModel: DriversViewModel by viewModels()

    private val driversAdapter by lazy {
        DriversAdapter(
            onFavoriteClick = { id ->
                viewModel.onEvent(DriversEvent.ToggleFavorite(id))
            }
        )
    }

    override fun bind() {
        setupRecycler()
        viewModel.onEvent(DriversEvent.LoadDrivers)

    }

    private fun setupRecycler() = with(binding) {
        rvDrivers.layoutManager = LinearLayoutManager(requireContext())
        rvDrivers.adapter = driversAdapter
        rvDrivers.setHasFixedSize(true)
    }

    override fun observers() {
        observeState()
        observeSideEffect()

    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                //binding.progressBar.isVisible = state.isLoading
                driversAdapter.submitList(state.drivers)
            }
        }
    }

    private fun observeSideEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    is DriversSideEffect.ShowError -> {
                        Snackbar.make(
                            binding.root,
                            effect.message,
                            Snackbar.LENGTH_LONG   //es garet gaitane
                        ).show()
                    }
                }
            }
        }
    }
}

