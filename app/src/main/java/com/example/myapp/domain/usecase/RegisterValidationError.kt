package com.example.myapp.domain.usecase

sealed class RegisterValidationError {
    data class MissingRequiredField(val fieldId: Int, val hint: String) : RegisterValidationError()
}