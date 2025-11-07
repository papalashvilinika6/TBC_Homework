package com.example.myapplication.register

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.register.RegisterViewModel
import com.example.myapplication.adapter.GroupAdapter
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private lateinit var viewModel: RegisterViewModel

    override fun listeners() {
    }

    override fun bind() {
        setupRecycler()
    }

    override fun observers() {
    }

    private fun setupRecycler(){
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        val groups = viewModel.loadFromJson(requireContext())
        val groupAdapter = GroupAdapter(groups)

        binding.recyclerGroups.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = groupAdapter
        }
    }


}