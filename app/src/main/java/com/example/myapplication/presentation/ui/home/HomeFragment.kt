package com.example.myapplication.presentation.ui.home

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.example.myapplication.databinding.FragmentHomeBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.data.repository.UsersRepository
import com.example.myapplication.presentation.adapter.UsersAdapter
import com.example.myapplication.presentation.viewmodel.UsersViewModel
import com.example.myapplication.presentation.viewmodel.UsersViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding> (FragmentHomeBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels {
        UsersViewModelFactory(UsersRepository(RetrofitClient.usersApi))
    }
    private val adapter = UsersAdapter(emptyList())

    override fun listeners() {
        profileBtn()
        viewModel.fetchUsers()
    }

    override fun bind() {
        setupRecycler()
    }

    override fun observers() {
        observeUsers()
        observeErrors()
    }

    fun profileBtn() {
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun setupRecycler() = with(binding) {
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter
    }

    private fun observeUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect { list ->
                    adapter.updateList(list)
                }
            }
        }
    }

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
