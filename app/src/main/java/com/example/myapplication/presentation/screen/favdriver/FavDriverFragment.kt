package com.example.myapplication.presentation.screen.favdriver

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavDriverBinding
import com.example.myapplication.presentation.screen.common.BaseFragment
import com.example.myapplication.presentation.screen.favdriver.adapter.DriversAdapter
import com.example.myapplication.presentation.utils.showSnack
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
        viewModel.onEvent(DriversEvent.LoadDrivers)
    }

    override fun listeners() {
        setupNextButton()
    }

    override fun observers() {
        observeState()
        observeSideEffect()
    }

    private fun setupNextButton() {
        binding.btnNext.setOnClickListener {
            val selectedDriverId = viewModel.state.value.selectedDriverId
            if (selectedDriverId == null) {
                binding.root.showSnack(getString(R.string.select_a_driver), Snackbar.LENGTH_SHORT)
                return@setOnClickListener
            }
            viewModel.onEvent(DriversEvent.SaveAndNavigate)
        }
    }

    private fun setupRecycler() = with(binding) {
        rvDrivers.layoutManager = LinearLayoutManager(requireContext())
        rvDrivers.adapter = driversAdapter
        rvDrivers.setHasFixedSize(true)
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
                            binding.root.showSnack(effect.error,
                                Snackbar.LENGTH_LONG)
                        }
                        DriversSideEffect.NavigateToHome -> {
                            try {
                                findNavController().navigate(R.id.action_favDriver_to_main)
                            } catch (e: Exception) {
                                binding.root.showSnack(
                                    getString(R.string.navigation_error, e.message),
                                    Snackbar.LENGTH_SHORT)
                            }
                        }
                    }
                }
            }
        }
    }
}
