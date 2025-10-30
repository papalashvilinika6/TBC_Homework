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
        btnDelivered()
        btnCanceled()
    }

    override fun bind() {
        updateState()
    }

    private fun updateButtonsVisibility(status: String) {
        val isPending = status == "PENDING"
        with(binding){
            btnDeliveredId.isEnabled = isPending
            btnCanceledId.isEnabled = isPending
            btnDeliveredId.alpha = if (isPending) 1f else 0.5f
            btnCanceledId.alpha = if (isPending) 1f else 0.5f
        }
    }

    private fun updateState(){
        val orderId = viewModel.selectedOrderId.value
        val currentStatus = viewModel.orders.value.firstOrNull { it.id == orderId }?.status ?: "PENDING"
        updateButtonsVisibility(currentStatus)
    }

    private fun btnCanceled() {
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

    private fun btnDelivered(){
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
    }
}