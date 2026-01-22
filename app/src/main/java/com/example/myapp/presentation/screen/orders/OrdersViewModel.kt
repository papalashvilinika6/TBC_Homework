package com.example.myapp.presentation.screen.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.domain.model.Order
import com.example.myapp.domain.model.OrderStatus
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * ViewModel backing the Orders screen.
 */
@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OrdersState())
    val state: StateFlow<OrdersState> = _state

    private val _sideEffect = Channel<OrdersSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: OrdersEvent) {
        when (event) {
            OrdersEvent.LoadOrders -> loadOrders()
            is OrdersEvent.SelectStatus -> selectStatus(event.status)
            is OrdersEvent.ClickOrder -> navigateToDetails(event.order)
            is OrdersEvent.UpdateOrderStatus -> updateOrderStatus(event.orderId, event.status)
        }
    }

    private fun loadOrders() {
        viewModelScope.launch {
            repository.getOrders().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            orders = resource.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                    is Resource.Loader -> {
                        _state.value = _state.value.copy(isLoading = resource.isLoading)
                    }
                }
            }
        }
    }

    private fun selectStatus(status: OrderStatus) {
        _state.value = _state.value.copy(currentStatus = status)
    }

    private fun navigateToDetails(order: Order) {
        viewModelScope.launch {
            _sideEffect.send(OrdersSideEffect.NavigateToDetails(order))
        }
    }

    private fun updateOrderStatus(orderId: Int, newStatus: OrderStatus) {
        val updatedOrders = _state.value.orders.map { order ->
            if (order.id == orderId) {
                order.copy(status = newStatus)
            } else {
                order
            }
        }
        _state.value = _state.value.copy(orders = updatedOrders)
    }
}
