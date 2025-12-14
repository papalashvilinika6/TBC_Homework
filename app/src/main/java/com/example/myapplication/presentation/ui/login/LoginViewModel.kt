package com.example.myapplication.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetCurrentUserUseCase
import com.example.myapplication.domain.usecase.SignInWithGoogleUseCase
import com.example.myapplication.domain.usecase.SignOutUseCase
import com.example.myapplication.domain.usecase.SignInWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val intentChannel = Channel<LoginIntent>(Channel.UNLIMITED)

    init {
        handleIntents()
    }

    fun processIntent(intent: LoginIntent) {
        viewModelScope.launch { intentChannel.send(intent) }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            for (intent in intentChannel) {
                when (intent) {
                    is LoginIntent.CheckSession -> observeCurrentUser()
                    is LoginIntent.SignInWithGoogle -> signInWithGoogle(intent.idToken)
                    is LoginIntent.SignInWithEmail -> signInWithEmail(intent.email, intent.password)
                    is LoginIntent.SignOut -> signOut()
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

    private fun signInWithGoogle(idToken: String) {
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

    private fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val user = signInWithEmailUseCase(email, password)
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