package com.example.myapplication.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.LoginResponseDto
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.utils.Resource
import com.example.myapplication.data.utils.utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    private val _navigationEvent = MutableSharedFlow<LoginEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _loginState = MutableStateFlow<Resource<LoginResponseDto>>(Resource.Loader(isLoading = false))
    val loginState = _loginState

    init {
        viewModelScope.launch {
            combine(_email, _password) { email, password ->
                utils.isEmailValid(email) && utils.isPasswordValid(password)
            }.collect { isValid ->
                _isButtonEnabled.value = isValid
            }
        }
    }

    fun onEvent(event: LoginEvent) = when (event) {
        is LoginEvent.OnEmailChanged -> onEmailChanged(event.email)
        is LoginEvent.OnPasswordChanged -> onPasswordChanged(event.password)
        is LoginEvent.Login -> login(event.email, event.password, event.rememberMe)
        LoginEvent.ClearToken -> clearToken()
        LoginEvent.EmitSuccessNavigation -> emitSuccessNavigation()
        else -> throw Exception("Invalid Event")
    }

    private fun onEmailChanged(value: String) { _email.value = value }
    private fun onPasswordChanged(value: String) { _password.value = value }

    private fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            repository.login(email, password, rememberMe).collect { result ->
                _loginState.value = result
                if (result is Resource.Success) {
                    _navigationEvent.emit(LoginEvent.Success)
                }
            }
        }
    }

    private fun clearToken() {
        viewModelScope.launch { repository.clearToken() }
    }

    private fun emitSuccessNavigation() {
        viewModelScope.launch { _navigationEvent.emit(LoginEvent.Success) }
    }

    suspend fun hasSavedToken(): Boolean {
        val remember = repository.getRememberMe()
        val token = repository.getToken()
        return remember && !token.isNullOrEmpty()
    }
}