package com.example.myapplication

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentAddressBinding

class AddressFragment : BaseFragment<FragmentAddressBinding>(FragmentAddressBinding::inflate) {

    private lateinit var adapter: AddressAdapter
    private val viewModel: AddressViewModel by activityViewModels()

    override fun listeners() {
    }

    override fun bind() {
        adapter = AddressAdapter(
            onItemClick = { address ->
                viewModel.selectAddress(address)
                findNavController().navigate(R.id.action_addressFragment_to_editFragment)
            },

            onItemLongClick = { address ->
                viewModel.deleteAddress(address)
            }
        )


        binding.rvAdressesId.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAdressesId.adapter = adapter

        viewModel.addresses.observe(viewLifecycleOwner) { userList ->
            adapter.submitList(userList)
        }

        btnAdd()
    }

    private fun btnAdd() = with(binding) {
        btnAddId.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_to_addFragment)
        }
    }


}