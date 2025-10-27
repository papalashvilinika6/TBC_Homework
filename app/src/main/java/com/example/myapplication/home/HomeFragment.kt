package com.example.myapplication.home

import android.R
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.game.GameViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: GameViewModel by activityViewModels()

    override fun listeners() {
        binding.btnStartGameId.setOnClickListener {
            val selectedSize = binding.spinnerBoardSizeId.selectedItemPosition + 3
            val action = HomeFragmentDirections.actionHomeFragmentToGameFragment(selectedSize)
            findNavController().navigate(action)
        }
    }

    override fun bind() {
        setupSpinner()
    }

    private fun setupSpinner() {
        val sizes = arrayOf("3x3", "4x4", "5x5")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, sizes)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerBoardSizeId.adapter = adapter
    }

}