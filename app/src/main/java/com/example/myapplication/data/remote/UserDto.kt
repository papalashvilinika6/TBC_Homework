package com.example.myapplication.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    @SerialName("full_name") val fullName: String,
    val email: String,
    @SerialName("activation_status") val activationStatus: Int,
    @SerialName("last_active_description") val lastActiveDescription: String,
    @SerialName("last_active_epoch") val lastActiveEpoch: Long,
    @SerialName("profile_image_url") val profileImageUrl: String?
)