package com.example.myapplication.address

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentAddressBinding

class AddressFragment : BaseFragment<FragmentAddressBinding>(FragmentAddressBinding::inflate) {

    private lateinit var adapter: AddressAdapter
    private val viewModel: AddressViewModel by activityViewModels()

    override fun listeners() {
        btnAdd()
    }

    override fun bind() {
        setupRecyclerView()
    }

    private fun btnAdd() = with(binding) {
        btnAddId.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_to_addFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = AddressAdapter(
            viewModel = viewModel,
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
    }
}