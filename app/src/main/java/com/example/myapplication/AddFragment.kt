package com.example.myapplication

import android.R
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar


class AddFragment : BaseFragment<FragmentAddBinding>(FragmentAddBinding::inflate) {

    private val viewModel: AddressViewModel by activityViewModels()

    override fun listeners() {
        backBtn()
        btnAdd()
    }

    override fun bind() {
        spinnerDestination()
    }

    private fun spinnerDestination() {
        val destinations = listOf("My Office", "My Home")

        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, destinations)
        binding.etDestinationId.adapter = adapter
    }

    private fun backBtn() {
        binding.btnBackId.setOnClickListener {
            findNavController().navigate(com.example.myapplication.R.id.action_addFragment_to_addressFragment)
        }
    }

    private fun btnAdd() {
        binding.btnAddId.setOnClickListener {
            val destination = binding.etDestinationId.selectedItem.toString()
            val address = binding.etAddressOneId.text.toString()

            if(etValidation(destination, address)){
                viewModel.addAddress(destination, address)
                findNavController().navigate(com.example.myapplication.R.id.action_addFragment_to_addressFragment)
            }
        }
    }

    private fun etValidation(destination: String, address: String): Boolean {
        return if(destination.isEmpty() || address.isEmpty()) {
            Snackbar.make(binding.root, "Enter All Fields", Snackbar.LENGTH_SHORT).show()
            false
        } else true
    }

}