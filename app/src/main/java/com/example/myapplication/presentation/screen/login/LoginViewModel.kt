package com.example.myapplication.presentation.screen.login

import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetCurrentUserUseCase
import com.example.myapplication.domain.usecase.SignInWithEmailUseCase
import com.example.myapplication.presentation.screen.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signInWithEmailUseCase: SignInWithEmailUseCase
) : BaseViewModel<LoginState, LoginEvent, LoginSideEffect>(
    initialState = LoginState()
) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.CheckSession -> observeCurrentUser()
            is LoginEvent.SignInWithEmail -> signInWithEmail(
                event.email,
                event.password
            )
        }
    }

    private fun observeCurrentUser() {
        getCurrentUserUseCase()
            .onEach { user ->
                updateState {
                    it.copy(
                        isLoading = false,
                        user = user,
                        error = null
                    )
                }

                if (user != null) {
                    emitSideEffect(LoginSideEffect.NavigateHome)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun signInWithEmail(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

            runCatching {
                signInWithEmailUseCase(email, password)
            }.onSuccess { user ->
                updateState {
                    it.copy(
                        isLoading = false,
                        user = user,
                        error = null
                    )
                }
                emitSideEffect(LoginSideEffect.NavigateHome)
            }.onFailure {
                updateState {
                    it.copy(
                        isLoading = false,
                        error = "Incorrect Fields!"
                    )
                }
            }
        }
    }
}
