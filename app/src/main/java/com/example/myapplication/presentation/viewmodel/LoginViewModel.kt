package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.utils.utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import com.example.myapplication.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed class LoginEvent {
    object Success : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val email: StateFlow<String> get() = _email
    val password: StateFlow<String> get() = _password

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    init {
        viewModelScope.launch {
            combine(_email, _password) { email, password ->
                utils.isEmailValid(email) && utils.isPasswordValid(password)
            }.collect { isValid ->
                _isButtonEnabled.value = isValid
            }
        }
    }

    fun onEmailChanged(value: String) { _email.value = value }
    fun onPasswordChanged(value: String) { _password.value = value }

    private val _navigationEvent = MutableSharedFlow<LoginEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            try {
                repository.login(email, password, rememberMe)
                _navigationEvent.emit(LoginEvent.Success)
            } catch (e: Exception) {
                _navigationEvent.emit(LoginEvent.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            repository.clearToken()
        }
    }

    fun emitSuccessNavigation() {
        viewModelScope.launch {
            _navigationEvent.emit(LoginEvent.Success)
        }
    }

    suspend fun hasSavedToken(): Boolean {
        val remember = repository.getRememberMe()
        val token = repository.getToken()
        return remember && !token.isNullOrEmpty()
    }

}

