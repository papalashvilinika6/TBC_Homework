package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.LoginRequest
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.model.RegisterRequest
import com.example.myapplication.model.RegisterResponse
import com.example.myapplication.remote.RetroFitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {
    private val _loginResult = MutableStateFlow<Result<LoginResponse>?>(null)
    val loginResult = _loginResult.asStateFlow()

    private val _registerResult = MutableStateFlow<Result<RegisterResponse>?>(null)
    val registerResult = _registerResult.asStateFlow()

    fun login(userName: String, password: String)  {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetroFitInstance.api.login(LoginRequest(userName, password))
                }
                _loginResult.value = Result.success(response)
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
            }

        }

    }

    fun register(email: String, userName: String, password: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetroFitInstance.api.register(RegisterRequest(email, userName, password))
                }
                _registerResult.value = Result.success(response)
            }catch (e: Exception) {
                _registerResult.value = Result.failure(e)
            }
        }
    }

}
