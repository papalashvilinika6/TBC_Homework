package com.example.myapplication.order_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentOrderListBinding
import com.example.myapplication.order.Order
import com.example.myapplication.order.OrderAdapter
import com.example.myapplication.order.OrderStatus
import kotlinx.coroutines.launch

class OrderListFragment : BaseFragment<FragmentOrderListBinding>(FragmentOrderListBinding::inflate) {

    private val viewModel: OrderListViewModel by activityViewModels()
    private lateinit var adapter: OrderAdapter

    private var orderStatus: OrderStatus = OrderStatus.ACTIVE
    private var onReviewClick: ((Order) -> Unit)? = null

    companion object {
        private const val ARG_STATUS = "status"

        fun newInstance(status: OrderStatus): OrderListFragment {
            return OrderListFragment().apply {
                arguments = Bundle().apply { putString(ARG_STATUS, status.name) }
            }
        }
    }

    fun setOnReviewClickListener(listener: (Order) -> Unit) {
        onReviewClick = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderStatus = OrderStatus.valueOf(it.getString(ARG_STATUS, OrderStatus.ACTIVE.name))
        }
    }

    override fun listeners() {
        setupRecyclerView()
        observeOrders()
    }

    override fun bind() {
    }

    private fun setupRecyclerView() {
        adapter = OrderAdapter(
            onReviewClick = { order -> onReviewClick?.invoke(order) },
            onBuyAgainClick = { order -> viewModel.buyAgain(order) }
        )
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = adapter
    }

    private fun observeOrders() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.orders.collect { orders ->
                    val filtered = orders.filter { it.status == orderStatus }
                    adapter.submitList(filtered)
                }
            }
        }
    }

}
