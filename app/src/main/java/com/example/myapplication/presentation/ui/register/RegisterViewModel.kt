package com.example.myapplication.presentation.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetCurrentUserUseCase
import com.example.myapplication.domain.usecase.SignInWithGoogleUseCase
import com.example.myapplication.domain.usecase.SignOutUseCase
import com.example.myapplication.domain.usecase.SignUpWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()
    private val intentChannel = Channel<RegisterIntent>(Channel.UNLIMITED)

    init { handleIntents() }

    fun processIntent(intent: RegisterIntent) {
        viewModelScope.launch { intentChannel.send(intent) }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            for (intent in intentChannel) {
                when (intent) {
                    is RegisterIntent.CheckSession -> observeCurrentUser()
                    is RegisterIntent.RegisterWithGoogle -> registerWithGoogle(intent.idToken)
                    is RegisterIntent.RegisterWithEmail -> registerWithEmail(
                        intent.email,
                        intent.password,
                        intent.name,
                        intent.phone
                    )
                    is RegisterIntent.SignOut -> signOut()
                }
            }
        }
    }

    private fun observeCurrentUser() {
        getCurrentUserUseCase()
            .onEach { user ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    user = user,
                    error = null
                )
            }
            .launchIn(viewModelScope)
    }

    private fun registerWithGoogle(idToken: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val user = signInWithGoogleUseCase(idToken)
                _state.value = _state.value.copy(isLoading = false, user = user, error = null)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
            }
        }
    }

    private fun registerWithEmail(email: String, password: String, name: String, phone: String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val user = signUpWithEmailUseCase(email, password, name, phone)
                _state.value = _state.value.copy(isLoading = false, user = user, error = null)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                signOutUseCase()
                _state.value = _state.value.copy(isLoading = false, user = null, error = null)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
            }
        }
    }
}