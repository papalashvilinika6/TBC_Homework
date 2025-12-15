package com.example.myapplication.data.common


import com.example.myapplication.domain.model.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

object HandleResponse{

    fun <T> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loader(isLoading = true))

        val response = call()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                emit(Resource.Success(data = body))
            } else {
                emit(Resource.Error(message = "Empty body"))
            }
        } else {
            val error = response.errorBody()?.string()
            emit(Resource.Error(message = error ?: "Unknown error"))
        }

    }.catch { e ->

        when (e) {
            is IOException -> {
                emit(Resource.Error(message = e.message ?: "Unknown error"))
            }

            is HttpException -> {
                emit(Resource.Error(message = e.message ?: "Unknown error"))
            }

            is IllegalStateException -> {
                emit(Resource.Error(message = e.message ?: "Unknown error"))
            }

            else -> {
                emit(Resource.Error(message = e.message ?: "Unknown error"))
            }
        }
    }

}
