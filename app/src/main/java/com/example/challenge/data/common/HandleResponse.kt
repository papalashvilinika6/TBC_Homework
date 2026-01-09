package com.example.challenge.data.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.challenge.domain.user_data_key.PreferenceKeys
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class HandleResponse() {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(loading = true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.Success(data = body))
            } else {
                emit(Resource.Error(errorMessage = response.errorBody()?.string() ?: ""))
            }
        } catch (e: Throwable) {
            emit(Resource.Error(errorMessage = e.message ?: ""))
        }
        emit(Resource.Loading(loading = false))
    }
}
