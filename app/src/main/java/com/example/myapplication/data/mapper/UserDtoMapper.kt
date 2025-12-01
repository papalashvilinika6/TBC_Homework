package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.UserResponseDto
import com.example.myapplication.domain.model.GetUsers
import com.example.myapplication.presentation.ui.home.UserResponse

fun UserResponseDto.toPresentation(): UserResponse {
    return UserResponse(
        page = page,
        perPage = perPage,
        total = total,
        totalPages = totalPages,
        data = data.map { user ->
            UserResponse.User(
                id = user.id,
                email = user.email,
                firstName = user.firstName,
                lastName = user.lastName,
                avatar = user.avatar
            )
        }
    )
}

fun UserResponseDto.UserDto.toDomain(): GetUsers {
    return GetUsers(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}


