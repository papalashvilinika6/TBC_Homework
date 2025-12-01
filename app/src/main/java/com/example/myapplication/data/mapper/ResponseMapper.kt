package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.LoginResponseDto
import com.example.myapplication.data.dto.RegisterResponseDto
import com.example.myapplication.domain.model.LoginResult
import com.example.myapplication.domain.model.RegisterResult

fun LoginResponseDto.toDomain(): LoginResult {
    return LoginResult(
        token = token
    )
}

fun RegisterResponseDto.toDomain(): RegisterResult {
    return RegisterResult(
        id = id,
        token = token
    )
}

