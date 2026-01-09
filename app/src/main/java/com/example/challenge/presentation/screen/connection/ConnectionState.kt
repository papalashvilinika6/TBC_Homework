package com.example.challenge.presentation.screen.connection

data class ConnectionState(
    val isLoading: Boolean = false,
    val connections: List<Connection> = emptyList(),
    val errorMessage: String? = null
)