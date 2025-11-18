package com.example.myapplication.data.common


import com.example.myapplication.data.utils.Resource
import retrofit2.Response

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Resource.Success(body)
            } else {
                Resource.Error("Response body is null")
            }
        } else {
            Resource.Error("Error ${response.code()}: ${response.message()}")
        }
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Unknown error")
    }
}

