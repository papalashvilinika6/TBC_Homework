package com.example.myapplication.domain.usecase.validate

import javax.inject.Inject

class ValidateRepeatPasswordUseCase @Inject constructor() {

    operator fun invoke(password: String, repeat: String): Boolean {
        return password == repeat
    }
}