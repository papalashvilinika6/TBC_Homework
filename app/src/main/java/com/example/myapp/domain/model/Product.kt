package com.example.myapp.domain.model

data class Product (
    val id: Int,
    val title: String,
    val price: String,
    val image: String?,
    val category: String
)