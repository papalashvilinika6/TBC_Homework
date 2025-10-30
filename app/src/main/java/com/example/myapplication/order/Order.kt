package com.example.myapplication.order

data class Order(
    val id: Int,
    val trackingNumber: String,
    val quantity: Int,
    val subtotal: Double,
    var status: String,
    val dateMillis: Long
)