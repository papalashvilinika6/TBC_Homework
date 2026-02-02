package com.example.myapp.data.remote.mapper

import com.example.myapp.data.remote.dto.RegisterFieldDto
import com.example.myapp.domain.model.FieldType
import com.example.myapp.domain.model.KeyboardType
import com.example.myapp.domain.model.RegisterField

fun RegisterFieldDto.toDomain(): RegisterField {
    val type = when (fieldType?.lowercase()) {
        "chooser" -> FieldType.CHOOSER
        else -> FieldType.INPUT
    }

    val kb = when (keyboard?.lowercase()) {
        "number" -> KeyboardType.NUMBER
        else -> KeyboardType.TEXT
    }

    val finalOptions =
        if (type == FieldType.CHOOSER && options.isEmpty() && hint.orEmpty().lowercase().contains("gender")) {
            listOf("Male", "Female", "Other")
        } else {
            options
        }

    return RegisterField(
        fieldId = fieldId ?: -1,
        hint = hint.orEmpty(),
        fieldType = type,
        keyboardType = kb,
        required = required,
        isActive = isActive,
        icon = icon,
        options = finalOptions
    )
}
