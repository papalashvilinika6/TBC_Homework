package com.example.myapplication.presentation.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.RegisterResponseDto
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.utils.Resource
import com.example.myapplication.data.utils.utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword: StateFlow<String> get() = _repeatPassword

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    private val _registerState = MutableStateFlow<Resource<RegisterResponseDto>>(Resource.Loader(false))
    val registerState = _registerState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<RegisterEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            combine(_email, _password, _repeatPassword) { email, pass, repeat ->
                utils.isEmailValid(email) &&
                        utils.isPasswordValid(pass) &&
                        pass == repeat
            }.collect { valid ->
                _isButtonEnabled.value = valid
            }
        }
    }

    fun onEvent(event: RegisterEvent) = when (event) {
        is RegisterEvent.OnEmailChanged -> _email.value = event.email
        is RegisterEvent.OnPasswordChanged -> _password.value = event.password
        is RegisterEvent.OnRepeatPasswordChanged -> _repeatPassword.value = event.password
        is RegisterEvent.Register -> register(event.email, event.password)
        RegisterEvent.Success -> emitSuccess()
    }

    private fun register(email: String, password: String) {
        viewModelScope.launch {
            repository.register(email, password).collect { result ->
                _registerState.value = result

                if (result is Resource.Success)
                    _navigationEvent.emit(RegisterEvent.Success)
            }
        }
    }

    private fun emitSuccess() {
        viewModelScope.launch {
            _navigationEvent.emit(RegisterEvent.Success)
        }
    }
}
