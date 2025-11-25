package com.example.myapplication.presentation.ui.home

import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.presentation.adapter.UsersPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private val adapter = UsersPagingAdapter()


    override fun listeners() {
        profileBtn()
        viewModel.onEvent(HomeEvent.FetchUsers())
    }

    override fun bind() {
        binding.rvUsers.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usersPaging.collect { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
//        setupRecycler()
    }

    override fun observers() {
//        observeUsers()
        observeErrors()
    }

    private fun profileBtn() {
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

//    private fun setupRecycler() = with(binding) {
//        rvUsers.layoutManager = LinearLayoutManager(requireContext())
//        rvUsers.adapter = adapter
//    }

//    private fun observeUsers() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.users.collect { list ->
//                    adapter.updateList(list)
//                }
//            }
//        }
//    }

    private fun observeErrors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { err ->
                    err?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show() }
                }
            }
        }
    }
}