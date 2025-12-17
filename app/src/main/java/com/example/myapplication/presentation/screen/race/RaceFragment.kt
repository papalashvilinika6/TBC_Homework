package com.example.myapplication.presentation.screen.race

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRaceBinding
import com.example.myapplication.presentation.screen.common.BaseFragment
import com.example.myapplication.presentation.screen.race.adapter.RaceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RaceFragment : BaseFragment<FragmentRaceBinding>(
    FragmentRaceBinding::inflate
) {

    private val viewModel: RaceViewModel by viewModels()
    private lateinit var adapter: RaceAdapter

    override fun bind() {
        setupRecycler()
        viewModel.onEvent(RaceEvent.Load2025)
    }

    override fun listeners() {
        binding.btn2025.setOnClickListener {
            viewModel.onEvent(RaceEvent.Load2025)
        }

        binding.btn2026.setOnClickListener {
            viewModel.onEvent(RaceEvent.Load2026)
        }
    }

    override fun observers() {
        observeState()
        observeSideEffects()
    }

    private fun setupRecycler() {
        adapter = RaceAdapter()
        binding.rvRaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRaces.adapter = adapter
        binding.rvRaces.itemAnimator = null
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->

                adapter.submit(
                    races = state.races,
                    is2025 = state.is2025
                )

                binding.progressBar.isVisible = state.isLoading

                updateSeasonButtons(state.is2025)
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    is RaceSideEffect.ShowError -> {
                        Toast.makeText(
                            requireContext(),
                            effect.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun updateSeasonButtons(is2025: Boolean) {
        if (is2025) {
            binding.btn2025.setBackgroundResource(R.drawable.bs_season_selected)
            binding.btn2026.setBackgroundResource(R.drawable.bg_season_unselected)
        } else {
            binding.btn2025.setBackgroundResource(R.drawable.bg_season_unselected)
            binding.btn2026.setBackgroundResource(R.drawable.bs_season_selected)
        }
    }

}
