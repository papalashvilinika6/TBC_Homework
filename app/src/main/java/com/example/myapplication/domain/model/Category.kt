package com.example.myapplication.domain.model

data class Category(
    val id: String,
    val name: String,
    val nameDe: String,
    val createdAt: String,
    val bglNumber: String? = null,
    val bglVariant: String? = null,
    val orderId: Int? = null,
    val main: String? = null,
    val children: List<Category> = emptyList(),
    val depth: Int = 0,
    val parentPath: List<String> = emptyList()
)

