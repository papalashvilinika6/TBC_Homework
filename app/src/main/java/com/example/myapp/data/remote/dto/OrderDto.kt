package com.example.myapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val id: Int,
    @SerialName("order_number") val orderNumber: String,
    val date: String,
    @SerialName("tracking_number")val trackingNumber: String,
    val quantity: Int,
    val subtotal: Double,
    val status: String
)

