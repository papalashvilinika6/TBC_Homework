package com.example.myapp.feature.register

import com.example.myapp.core.common.UiText
import com.example.myapp.domain.model.RegisterField

data class RegisterState(
    val isLoading: Boolean = false,
    val config: List<List<RegisterField>> = emptyList(),
    val values: Map<Int, String> = emptyMap(),
    val lastPayload: Map<String, String> = emptyMap(),
    val snackbarMessage: UiText? = null
)

sealed interface RegisterEvent {
    data object Load : RegisterEvent
    data class OnValueChange(val fieldId: Int, val value: String) : RegisterEvent
    data object OnRegisterClick : RegisterEvent
    data object OnSnackbarShown : RegisterEvent
}
