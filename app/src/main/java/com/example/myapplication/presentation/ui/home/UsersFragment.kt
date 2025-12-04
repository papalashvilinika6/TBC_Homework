package com.example.myapplication.presentation.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentUsersBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.common.showSnack
import com.example.myapplication.presentation.ui.utils.hide
import com.example.myapplication.presentation.ui.utils.show
import com.google.android.material.snackbar.Snackbar

@AndroidEntryPoint
class UsersFragment :
    BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var adapter: UsersAdapter

    override fun bind() {
        setupRecycler()
        viewModel.onEvent(UsersEvent.Load)
    }

    override fun observers() {
        observeState()
        observeSideEffects()
    }

    private fun setupRecycler() {
        adapter = UsersAdapter()
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsers.adapter = adapter
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->

                adapter.submitList(state.users)

                binding.progressBar.apply {
                    if (state.isLoading) show() else hide()
                }


                binding.tvActivation.apply {
                    text = if (state.isOnline) context.getString(R.string.y_online)
                        else context.getString(R.string.y_offline)
                    setTextColor(
                        if (state.isOnline)
                            requireContext().getColor(R.color.green)
                        else
                            requireContext().getColor(R.color.red)
                    )
                }
            }
        }
    }

    private fun observeSideEffects() {
        lifecycleScope.launch {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    UsersSideEffect.ShowOffline -> {
                        binding.root.showSnack(getString(R.string.offline), Snackbar.LENGTH_SHORT)
                    }
                }
            }
        }
    }
}
