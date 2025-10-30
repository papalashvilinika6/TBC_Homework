package com.example.myapplication.details

import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentDetailsBinding
import androidx.fragment.app.activityViewModels
import com.example.myapplication.viewmodel.ItemsViewModel
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private val viewModel: ItemsViewModel by activityViewModels()

    override fun listeners() {
        binding.btnDeliveredId.setOnClickListener {
            val orderId = viewModel.selectedOrderId.value ?: return@setOnClickListener
            val order = viewModel.orders.value.firstOrNull { it.id == orderId } ?: return@setOnClickListener
            if (order.status == "PENDING") {
                viewModel.updateOrderStatus(orderId, "DELIVERED")
                updateButtonsVisibility("DELIVERED")
                viewModel.selectStatusByTitle("DELIVERED")
                findNavController().popBackStack()
            }
        }

        binding.btnCanceledId.setOnClickListener {
            val orderId = viewModel.selectedOrderId.value ?: return@setOnClickListener
            val order = viewModel.orders.value.firstOrNull { it.id == orderId } ?: return@setOnClickListener
            if (order.status == "PENDING") {
                viewModel.updateOrderStatus(orderId, "CANCELED")
                updateButtonsVisibility("CANCELED")
                viewModel.selectStatusByTitle("CANCELED")
                findNavController().popBackStack()
            }
        }
    }

    override fun bind() {
        val orderId = viewModel.selectedOrderId.value
        val currentStatus = viewModel.orders.value.firstOrNull { it.id == orderId }?.status ?: "PENDING"
        updateButtonsVisibility(currentStatus)
    }

    override fun observers() {
        // No-op for now
    }

    private fun updateButtonsVisibility(status: String) {
        val isPending = status == "PENDING"
        binding.btnDeliveredId.isEnabled = isPending
        binding.btnCanceledId.isEnabled = isPending
        binding.btnDeliveredId.alpha = if (isPending) 1f else 0.5f
        binding.btnCanceledId.alpha = if (isPending) 1f else 0.5f
    }
}