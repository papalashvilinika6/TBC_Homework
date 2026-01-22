package com.example.myapp.presentation.screen.orders

import com.example.myapp.domain.model.Order
import com.example.myapp.domain.model.OrderStatus

data class OrdersState(
    val orders: List<Order> = emptyList(),
    val currentStatus: OrderStatus = OrderStatus.PENDING,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val filteredOrders: List<Order>
        get() = orders.filter { it.status == currentStatus }
}

sealed interface OrdersEvent {
    object LoadOrders : OrdersEvent
    data class SelectStatus(val status: OrderStatus) : OrdersEvent
    data class ClickOrder(val order: Order) : OrdersEvent
    data class UpdateOrderStatus(val orderId: Int, val status: OrderStatus) : OrdersEvent
}

sealed interface OrdersSideEffect {
    data class NavigateToDetails(val order: Order) : OrdersSideEffect
}
