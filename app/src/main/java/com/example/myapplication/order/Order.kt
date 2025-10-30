package com.example.myapplication.order

data class Order(
    val orderId: Int,
    val date: String,
    val trackingNumber: String,
    val quantity: Int,
    val subtotal: Double,
    val status: String
)