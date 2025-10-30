package com.example.myapplication.status

data class Status(
    val id: Int,
    val title: String,
    val isSelected: Boolean = false
)