package com.example.myapplication.order_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.order.Order
import com.example.myapplication.order.OrderStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderListViewModel : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()


    private val _openReviewSheet = MutableSharedFlow<Order>()
    val openReviewSheet = _openReviewSheet.asSharedFlow()

    init {
        _orders.value = listOf(
            Order(
                "1",
                "Modern Wingback",
                "$280.00",
                "Black",
                2,
                OrderStatus.COMPLETED,
                R.drawable.chair2
            ),
            Order("2",
                "Active Order 1",
                "$100.00",
                "Red",
                1,
                OrderStatus.ACTIVE,
                R.drawable.chair2)
        )
    }

    fun requestReview(order: Order) {
        viewModelScope.launch {
            _openReviewSheet.emit(order)
        }
    }

    fun leaveReview(order: Order) {
        val updated = _orders.value.map {
            if (it.id == order.id) it.copy(reviewed = true) else it
        }
        _orders.value = updated
    }

    fun buyAgain(order: Order) {}
}