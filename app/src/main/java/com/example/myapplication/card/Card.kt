package com.example.myapplication.card

data class Card(
    val id: Int,
    val cardNumber: String,
    val name: String,
    val valid: String,
    val cvv: Int,
    val type: Type
)

enum class Type(val displayName: String) {
    MASTERCARD("MasterCard"),
    VISA("Visa")
}
