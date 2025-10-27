package com.example.myapplication.edit

import android.R
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.address.AddressViewModel
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentEditBinding
import com.google.android.material.snackbar.Snackbar

class EditFragment : BaseFragment<FragmentEditBinding>(FragmentEditBinding::inflate) {

    val destinations = listOf("My Office", "My Home")

    private val viewModel: AddressViewModel by activityViewModels()

    override fun listeners() {
        backBtn()
        btnAdd()
    }

    override fun bind() {
        spinnerDestination()
        etUpdate()
    }

    private fun spinnerDestination() {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, destinations)
        binding.etDestinationId.adapter = adapter
    }

    private fun backBtn() {
        binding.btnBackId.setOnClickListener {
            findNavController().navigate(com.example.myapplication.R.id.action_editFragment_to_addressFragment)
        }
    }

    private fun etUpdate() {
        viewModel.selectedAddress.observe(viewLifecycleOwner) { address ->
            address?.let {
                binding.etAddressOneId.setText(it.subtitle)

                val position = destinations.indexOf(it.title)
                if (position >= 0) {
                    binding.etDestinationId.setSelection(position)
                }
            }
        }
    }

    private fun btnAdd() {
        binding.btnAddId.setOnClickListener {
            val updatedDestination = binding.etDestinationId.selectedItem.toString().trim()
            val updatedAddress = binding.etAddressOneId.text.toString().trim()

            if(etValidation(updatedDestination, updatedAddress)) {
                val current = viewModel.selectedAddress.value
                if (current != null) {
                    val updated = current.copy(
                        title = updatedDestination,
                        subtitle = updatedAddress
                    )
                    viewModel.updateAddress(updated)
                }

                findNavController().navigateUp()
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