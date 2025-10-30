package com.example.myapplication.order

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

    lateinit var orderAdapter: OrderAdapter
    lateinit var statusAdapter: StatusAdapter
    private val viewModel : ItemsViewModel by activityViewModels()

    override fun listeners() {
    }

    override fun bind() {
        setupOrders()
        setupStatus()
    }


    override fun observers() {
        observeOrder()
        observeStatus()
    }

    private fun setupOrders(){
        orderAdapter = OrderAdapter { order ->
            viewModel.selectOrder(order.id)
            findNavController().navigate(R.id.action_orderFragment_to_detailsFragment)
        }
        binding.rvOrdersId.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrdersId.adapter = orderAdapter
    }

    private fun setupStatus(){
        statusAdapter = StatusAdapter { selected ->
            viewModel.selectStatus(selected)
        }
        binding.rvStatusId.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvStatusId.adapter = statusAdapter
    }

    private fun observeStatus(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.statuses.collectLatest { statuses ->
                    statusAdapter.submitList(statuses)
                }
            }
        }
    }

    private fun observeOrder(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sortedOrders.collectLatest { orderList ->
                    orderAdapter.submitList(orderList)
                }
            }
        }
    }

}