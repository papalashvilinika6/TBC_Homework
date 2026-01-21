package com.example.myapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto (
    val id: Int,
    val title: String,
    val price: String,
    val image: String?,
    val category: String
)