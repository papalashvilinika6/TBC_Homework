package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.order.Order
import com.example.myapplication.status.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn


class ItemsViewModel : ViewModel() {

    private val _statuses = MutableStateFlow(
        listOf(
            Status(1, "DELIVERED"),
            Status(2, "PENDING"),
            Status(3, "CANCELED")
        )
    )
    val statuses: StateFlow<List<Status>> = _statuses


    private val _orders = MutableStateFlow(
        listOf(
            Order(1, "TRK123", 2, 49.99, "PENDING", System.currentTimeMillis()),
            Order(2, "TRK124", 1, 29.99, "PENDING", System.currentTimeMillis()),
            Order(3, "TRK125", 3, 89.99, "PENDING", System.currentTimeMillis()),
            Order(4, "TRK126", 1, 19.99, "PENDING", System.currentTimeMillis())
        )
    )
    val orders: StateFlow<List<Order>> = _orders

    private val _selectedStatusId = MutableStateFlow<Int?>(2)
    val selectedStatusId: StateFlow<Int?> = _selectedStatusId

    private val statusSortOrder = listOf("DELIVERED", "CANCELED", "PENDING")

    val sortedOrders: StateFlow<List<Order>> = combine(_orders, _selectedStatusId) { orders, selectedId ->
        val filtered = if (selectedId == null) orders else {
            val status = _statuses.value.firstOrNull { it.id == selectedId }?.title
            if (status != null) orders.filter { it.status == status } else orders
        }
        filtered.sortedWith(compareBy({ statusSortOrder.indexOf(it.status) }, { -it.dateMillis }))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun selectStatus(selected: Status) {
        val updatedStatuses = _statuses.value.map { it.copy(isSelected = it.id == selected.id) }
        _statuses.value = updatedStatuses
        _selectedStatusId.value = selected.id
    }


    fun updateOrderStatus(orderId: Int, newStatus: String) {
        _orders.value = _orders.value.map { order ->
            if (order.id == orderId) order.copy(status = newStatus) else order
        }
    }

    private val _selectedOrderId = MutableStateFlow<Int?>(null)
    val selectedOrderId: StateFlow<Int?> = _selectedOrderId

    fun selectOrder(orderId: Int) {
        _selectedOrderId.value = orderId
    }

    fun selectStatusByTitle(title: String) {
        val target = _statuses.value.firstOrNull { it.title == title }
        if (target != null) selectStatus(target)
    }
}