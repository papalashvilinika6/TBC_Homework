package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.data.dto.RegisterRequestDto
import com.example.myapplication.data.dto.RegisterResponseDto
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {

    private val _registerResult = MutableStateFlow<Result<RegisterResponseDto>?>(null)
    val registerResult = _registerResult.asStateFlow()

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                // The withContext block now returns the parsed body or throws an error
                val registerResponse = withContext(Dispatchers.IO) {
                    // 1. Execute the network call
                    val response = RetrofitClient.registerApiService.register(RegisterRequestDto(email, password))
                    // 2. Check if the response was successful (HTTP 200-299)
                    if (response.isSuccessful) {
                        // 3. Return only the body if successful
                        response.body() ?: throw Exception("Response body is null")
                    } else {
                        // 4. Throw an exception with the error message if not successful
                        throw Exception("Login failed: ${response.errorBody()?.string()}")
                    }
                }
                // Now, 'loginResponse' is of type LoginResponseDto
                _registerResult.value = Result.success(registerResponse)
            } catch (e: Exception) {
                // All exceptions (network errors, server errors) are caught here
                _registerResult.value = Result.failure(e)
            }
        }
    }
}