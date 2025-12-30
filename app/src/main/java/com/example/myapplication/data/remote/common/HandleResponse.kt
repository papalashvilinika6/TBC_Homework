package com.example.myapplication.data.remote.common

import com.example.myapplication.domain.model.AppError
import com.example.myapplication.domain.model.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

object HandleResponse {

    fun <T> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loader(isLoading = true))

        val response = call()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                emit(Resource.Success(data = body))
            } else {
                val error = AppError.EmptyBodyError()
                emit(Resource.Error(message = error.getUserMessage()))
            }
        } else {
            val errorBody = response.errorBody()?.string()
            val error = AppError.HttpError(
                code = response.code(),
                message = errorBody ?: "HTTP ${response.code()}: ${response.message()}"
            )
            emit(Resource.Error(message = error.getUserMessage()))
        }
    }.catch { e ->
        val appError = when (e) {
            is IOException -> {
                AppError.NetworkError(
                    message = e.message ?: "Network error occurred",
                    throwable = e
                )
            }

            is HttpException -> {
                AppError.HttpError(
                    code = e.code(),
                    message = e.message() ?: "HTTP error occurred",
                    throwable = e
                )
            }

            is IllegalStateException -> {
                AppError.UnknownError(
                    message = e.message ?: "An unexpected error occurred",
                    throwable = e
                )
            }

            else -> {
                AppError.UnknownError(
                    message = e.message ?: "An unknown error occurred",
                    throwable = e
                )
            }
        }
        emit(Resource.Error(message = appError.getUserMessage()))
    }
}

