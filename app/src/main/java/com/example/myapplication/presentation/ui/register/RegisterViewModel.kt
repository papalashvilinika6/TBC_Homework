package com.example.myapplication.presentation.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.RegisterRequestDto
import com.example.myapplication.data.dto.RegisterResponseDto
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registerResult = MutableStateFlow<Result<RegisterResponseDto>?>(null)
    val registerResult = _registerResult.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> register(event.email, event.password)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            when (val result = repository.register(email, password)) {
                is Resource.Success -> {
                    _registerResult.value = Result.success(result.data!!)
                }
                is Resource.Error -> _registerResult.value = Result.failure(Exception(result.message))
                is Resource.Loading<*> -> {}
            }
        }
    }

}