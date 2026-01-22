package com.example.myapp.domain.model

enum class OrderStatus {
    PENDING, DELIVERED, CANCELED;

    fun displayName(): String = name.lowercase().replaceFirstChar { it.titlecase() }
}