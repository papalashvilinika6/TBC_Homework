package com.example.myapp.domain.model

data class Order(
    val id: Int,
    val orderNumber: String,
    val dateMillis: Long,
    val trackingNumber: String,
    val quantity: Int,
    val subtotal: Double,
    val status: OrderStatus
)
