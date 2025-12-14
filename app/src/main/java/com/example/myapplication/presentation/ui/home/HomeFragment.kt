package com.example.myapplication.presentation.ui.home

import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding> (FragmentHomeBinding::inflate) {

    override fun bind() {
        binding.messageText.text = "Welcome Home!"
    }

}
