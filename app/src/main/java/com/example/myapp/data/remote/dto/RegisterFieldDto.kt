package com.example.myapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterFieldDto(
    @SerialName("field_id") val fieldId: Int? = null,
    @SerialName("hint") val hint: String? = null,
    @SerialName("field_type") val fieldType: String? = null,
    @SerialName("keyboard") val keyboard: String? = null,
    @SerialName("required") val required: Boolean = false,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("icon") val icon: String? = null,
    @SerialName("options") val options: List<String> = emptyList()
)
