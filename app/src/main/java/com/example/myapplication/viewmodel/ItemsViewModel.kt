package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.order.Order
import com.example.myapplication.status.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ItemsViewModel : ViewModel() {

    private val _statuses = MutableStateFlow(
        listOf(
            Status(1, "Delivered", true),
            Status(2, "Pending"),
            Status(3, "Canceled")
        )
    )
    val statuses: StateFlow<List<Status>> = _statuses


    private val _orders = MutableStateFlow(
        listOf(
            Order(1, "2025-10-01", "TRK123", 2, 49.99, "Pending"),
            Order(2, "2025-10-03", "TRK124", 1, 29.99, "Pending"),
            Order(3, "2025-10-05", "TRK125", 3, 89.99, "Pending"),
            Order(4, "2025-10-06", "TRK126", 1, 19.99, "Pending")
        )
    )
    val orders: StateFlow<List<Order>> = _orders
    

    fun selectStatus(selected: Status) {
        val updatedStatuses = _statuses.value.map {
            it.copy(isSelected = it.id == selected.id)
        }
        _statuses.value = updatedStatuses
    }
}