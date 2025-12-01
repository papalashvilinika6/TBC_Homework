package com.example.myapplication.presentation.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.model.RegisterResult
import com.example.myapplication.domain.usecase.auth.RegisterUseCase
import com.example.myapplication.domain.usecase.validate.ValidateEmailUseCase
import com.example.myapplication.domain.usecase.validate.ValidatePasswordUseCase
import com.example.myapplication.domain.usecase.validate.ValidateRepeatPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatPassword: ValidateRepeatPasswordUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _repeatPassword = MutableStateFlow("")

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    private val _registerState =
        MutableStateFlow<Resource<RegisterResult>>(Resource.Loader(false))

    val registerState: StateFlow<Resource<RegisterResult>> = _registerState

    private val _navigationEvent = MutableSharedFlow<RegisterEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            combine(_email, _password, _repeatPassword) { email, pass, repeat ->
                validateEmail(email) &&
                        validatePassword(pass) &&
                        validateRepeatPassword(pass, repeat)
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
            registerUseCase(email, password).collect { result ->
                _registerState.value = result

                if (result is Resource.Success) {
                    _navigationEvent.emit(RegisterEvent.Success)
                }
            }
        }
    }


    private fun emitSuccess() {
        viewModelScope.launch {
            _navigationEvent.emit(RegisterEvent.Success)
        }
    }
}
