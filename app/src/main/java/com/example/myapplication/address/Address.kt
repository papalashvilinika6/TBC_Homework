package com.example.myapplication.address

data class Address(
    val id: Int,
    val title: String,
    val subtitle: String,
    val typeIcon: Int,
    val isSelected: Boolean = false
)