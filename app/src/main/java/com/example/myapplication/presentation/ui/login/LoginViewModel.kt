package com.example.myapplication.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.model.LoginResult
import com.example.myapplication.domain.usecase.auth.LoginUseCase
import com.example.myapplication.domain.usecase.local.SaveRememberMeUseCase
import com.example.myapplication.domain.usecase.local.SaveTokenUseCase
import com.example.myapplication.domain.usecase.validate.ValidateEmailUseCase
import com.example.myapplication.domain.usecase.validate.ValidatePasswordUseCase
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
    private val loginUseCase: LoginUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveRememberMeUseCase: SaveRememberMeUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    private val _navigationEvent = MutableSharedFlow<LoginEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    init {
        viewModelScope.launch {
            combine(_email, _password) { email, password ->
                validateEmail(email) && validatePassword(password)
            }.collect { isValid ->
                _isButtonEnabled.value = isValid
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> onEmailChanged(event.email)
            is LoginEvent.OnPasswordChanged -> onPasswordChanged(event.password)
            is LoginEvent.Login -> login(event.email, event.password, event.rememberMe)
            LoginEvent.EmitSuccessNavigation -> emitSuccessNavigation()
            LoginEvent.Success -> emitSuccessNavigation()
            LoginEvent.ClearToken -> clearToken()
        }
    }

    private fun clearToken() {
        viewModelScope.launch {
            saveTokenUseCase("")
            saveRememberMeUseCase(false)
        }
    }

    private fun onEmailChanged(value: String) { _email.value = value }
    private fun onPasswordChanged(value: String) { _password.value = value }

    private fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                if (result is Resource.Success) {
                    saveTokenUseCase(result.data.token)
                    saveRememberMeUseCase(rememberMe)
                    _navigationEvent.emit(LoginEvent.Success)
                }
            }
        }
    }

    private fun emitSuccessNavigation() {
        viewModelScope.launch { _navigationEvent.emit(LoginEvent.Success) }
    }

}