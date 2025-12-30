package com.example.myapplication.domain.model

sealed class AppError(
    open val message: String,
    open val throwable: Throwable? = null
) {
    data class NetworkError(
        override val message: String,
        override val throwable: Throwable? = null
    ) : AppError(message, throwable)

    data class HttpError(
        val code: Int,
        override val message: String,
        override val throwable: Throwable? = null
    ) : AppError(message, throwable)

    data class EmptyBodyError(
        override val message: String = "Empty response body"
    ) : AppError(message)

    data class UnknownError(
        override val message: String = "An unknown error occurred",
        override val throwable: Throwable? = null
    ) : AppError(message, throwable)

    fun getUserMessage(): String = message
}

