package com.example.myapplication.presentation.ui.favdriver

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
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
                viewModel.onEvent(DriversEvent.SelectDriver(id))
            }
        )
    }

    override fun bind() {
        setupRecycler()
        setupBackButton()
        setupNextButton()
        viewModel.onEvent(DriversEvent.LoadDrivers)
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupNextButton() {
        binding.btnNext.setOnClickListener {
            // Check if a driver is selected before proceeding
            val selectedDriverId = viewModel.state.value.selectedDriverId
            if (selectedDriverId == null) {
                Snackbar.make(
                    binding.root,
                    "Please select a driver first",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            // Trigger save and navigate
            viewModel.onEvent(DriversEvent.SaveAndNavigate)
        }
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    driversAdapter.submitList(state.drivers)
                }
            }
        }
    }

    private fun observeSideEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        is DriversSideEffect.ShowError -> {
                            Snackbar.make(
                                binding.root,
                                effect.message,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        DriversSideEffect.NavigateToHome -> {
                            try {
                                findNavController().navigate(R.id.action_favDriver_to_main)
                            } catch (e: Exception) {
                                Snackbar.make(
                                    binding.root,
                                    "Navigation error: ${e.message}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
