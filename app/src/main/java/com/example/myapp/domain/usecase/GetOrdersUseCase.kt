package com.example.myapp.domain.usecase

import com.example.myapp.domain.repository.OrderRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke() = repository.getOrders()
}