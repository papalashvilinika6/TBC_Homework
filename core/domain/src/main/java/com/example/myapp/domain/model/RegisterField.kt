package com.example.myapp.domain.model

data class RegisterField(
    val fieldId: Int,
    val hint: String,
    val fieldType: FieldType,
    val keyboardType: KeyboardType,
    val required: Boolean,
    val isActive: Boolean,
    val icon: String?,
    val options: List<String> = emptyList()
)

enum class FieldType { INPUT, CHOOSER }
enum class KeyboardType { TEXT, NUMBER }
