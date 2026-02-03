package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.RegisterField
import javax.inject.Inject


class ValidateRegisterUseCase @Inject constructor() {

    operator fun invoke(
        config: List<List<RegisterField>>,
        values: Map<Int, String>
    ): ValidationResult {
        val missingRequired = config
            .asSequence()
            .flatten()
            .filter { it.isActive }
            .firstOrNull { field ->
                field.required && values[field.fieldId].orEmpty().trim().isBlank()
            }

        return if (missingRequired == null) {
            ValidationResult(isValid = true)
        } else {
            ValidationResult(
                isValid = false,
                error = RegisterValidationError.MissingRequiredField(
                    fieldId = missingRequired.fieldId,
                    hint = missingRequired.hint
                )
            )
        }
    }
}
