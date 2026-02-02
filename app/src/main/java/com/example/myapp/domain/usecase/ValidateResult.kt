package com.example.myapp.domain.usecase

data class ValidationResult(
    val isValid: Boolean,
    val error: RegisterValidationError? = null
)