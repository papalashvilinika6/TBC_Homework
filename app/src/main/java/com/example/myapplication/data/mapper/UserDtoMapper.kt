package com.example.myapplication.data.mapper

import com.example.myapplication.data.local.UserEntity
import com.example.myapplication.data.remote.UserDto
import com.example.myapplication.domain.model.User

fun UserDto.toEntity() = UserEntity(
    id = id,
    fullName = fullName,
    email = email,
    activationStatus = activationStatus,
    lastActiveDescription = lastActiveDescription,
    lastActiveEpoch = lastActiveEpoch,
    profileImageUrl = profileImageUrl
)

fun UserEntity.toDomain() = User(
    id = id,
    fullName = fullName,
    email = email,
    activationStatus = activationStatus,
    lastActiveDescription = lastActiveDescription,
    lastActiveEpoch = lastActiveEpoch,
    profileImageUrl = profileImageUrl
)



