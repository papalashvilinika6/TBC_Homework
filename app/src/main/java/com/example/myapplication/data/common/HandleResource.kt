package com.example.myapplication.data.common


import com.example.myapplication.data.util.Resource
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

class HandleResponse{

    fun <T> safeApiCall(call: suspend () -> Response<T>) = flow{
        emit(Resource.Loading(true))
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    emit(Resource.Success(data = it))
                }
            } else {
                val error = response.errorBody()?.string()
                emit(Resource.Error(message = error ?: "Unknown error"))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        } catch (e: IllegalStateException) {
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }
}