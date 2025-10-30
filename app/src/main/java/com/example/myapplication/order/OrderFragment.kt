package com.example.myapplication.order

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentOrderBinding
import com.example.myapplication.status.StatusAdapter
import com.example.myapplication.viewmodel.ItemsViewModel
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {

    lateinit var adapter: OrderAdapter
    lateinit var statusAdapter: StatusAdapter
    private val viewModel : ItemsViewModel by activityViewModels()

    override fun listeners() {
    }

    override fun bind() {
    }

    override fun observers() {
        adapter = OrderAdapter { order ->
            viewModel.selectOrder(order.id)
            findNavController().navigate(R.id.action_orderFragment_to_detailsFragment)
        }
        binding.rvOrdersId.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrdersId.adapter = adapter

        statusAdapter = StatusAdapter { selected ->
            viewModel.selectStatus(selected)
        }
        binding.rvStatusId.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvStatusId.adapter = statusAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.statuses.collectLatest { statuses ->
                statusAdapter.submitList(statuses)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sortedOrders.collectLatest { orderList ->
                adapter.submitList(orderList)
            }
        }
    }


}