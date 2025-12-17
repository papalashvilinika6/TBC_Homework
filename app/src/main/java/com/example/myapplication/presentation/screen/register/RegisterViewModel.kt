package com.example.myapplication.presentation.screen.register

import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetCurrentUserUseCase
import com.example.myapplication.domain.usecase.SignUpWithEmailUseCase
import com.example.myapplication.presentation.screen.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
) : BaseViewModel<RegisterState, RegisterEvent, RegisterSideEffect>(
    initialState = RegisterState()
) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.CheckSession -> observeCurrentUser()
            is RegisterEvent.RegisterWithEmail -> registerWithEmail(
                event.email,
                event.password,
                event.name
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
                    emitSideEffect(RegisterSideEffect.NavigateHome)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun registerWithEmail(
        email: String,
        password: String,
        name: String
    ) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

            runCatching {
                signUpWithEmailUseCase(email, password, name)
            }.onSuccess { user ->
                updateState {
                    it.copy(
                        isLoading = false,
                        user = user,
                        error = null
                    )
                }
                emitSideEffect(RegisterSideEffect.NavigateHome)
            }.onFailure { e ->
                updateState {
                    it.copy(
                        isLoading = false,
                        error = "Invalid Fields!"
                    )
                }
            }
        }
    }
}
