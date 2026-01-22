package com.example.myapp.data.remote.mapper

import com.example.myapp.data.remote.dto.OrderDto
import com.example.myapp.domain.model.Order
import com.example.myapp.domain.model.OrderStatus
import java.text.SimpleDateFormat
import java.util.Locale

object OrderMapper {

    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    fun OrderDto.toDomain(): Order =
        Order(
            id = id,
            orderNumber = orderNumber,
            dateMillis = sdf.parse(date)?.time ?: 0L,
            trackingNumber = trackingNumber,
            quantity = quantity,
            subtotal = subtotal,
            status = OrderStatus.valueOf(status)
        )
}
