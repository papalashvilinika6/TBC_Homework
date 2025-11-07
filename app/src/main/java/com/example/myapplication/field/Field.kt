package com.example.myapplication.field

data class Field(
    val fieldId: Int,
    val hint: String,
    val fieldType: FieldType,
    val keyboard: String? = null,
    val required: Boolean,
    val isActive: Boolean,
    val icon: String
)

enum class FieldType {
    INPUT,
    CHOOSER
}

