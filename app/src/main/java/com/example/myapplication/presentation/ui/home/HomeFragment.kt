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
import com.example.myapplication.presentation.viewmodel.UsersViewModel
import com.example.myapplication.presentation.viewmodel.UsersViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding> (FragmentHomeBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels {
        UsersViewModelFactory(UsersRepository(RetrofitClient.usersApi))
    }

    override fun listeners() {
        profileBtn()
    }

    override fun bind() {
        fetchUsers()
        setupRecycler()
    }

    fun profileBtn() {
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun setupRecycler() = with(binding) {
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun fetchUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect { list ->
                    binding.rvUsers.adapter = UsersAdapter(list)
                }
            }
        }
        viewModel.fetchUsers()
    }

}
