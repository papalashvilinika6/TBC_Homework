package com.example.myapp.presentation.screen.register

import androidx.lifecycle.viewModelScope
import com.example.myapp.R
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.usecase.GetRegisterConfigUseCase
import com.example.myapp.domain.usecase.RegisterValidationError
import com.example.myapp.domain.usecase.ValidateRegisterUseCase
import com.example.myapp.presentation.common.BaseViewModel
import com.example.myapp.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getRegisterConfig: GetRegisterConfigUseCase,
    private val validateRegister: ValidateRegisterUseCase
) : BaseViewModel<RegisterState, RegisterEvent>(
    initialState = RegisterState()
) {

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.Load -> load()
            is RegisterEvent.OnValueChange -> onValueChange(event.fieldId, event.value)
            RegisterEvent.OnRegisterClick -> onRegister()
            RegisterEvent.OnSnackbarShown -> clearSnackbar()
        }
    }

    private fun load() {
        viewModelScope.launch {
            getRegisterConfig().collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        updateState { it.copy(isLoading = resource.isLoading) }
                    }

                    is Resource.Success -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                config = resource.data
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                snackbarMessage = UiText.Dynamic(resource.message)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onValueChange(fieldId: Int, value: String) {
        updateState { st ->
            st.copy(values = st.values.toMutableMap().apply { put(fieldId, value) })
        }
    }

    private fun onRegister() {
        val st = state.value
        val validation = validateRegister(st.config, st.values)

        if (!validation.isValid) {
            val msg: UiText = when (val err = validation.error) {
                is RegisterValidationError.MissingRequiredField -> {
                    UiText.Resource(
                        resId = R.string.error_empty_field,
                        args = listOf(err.hint)
                    )
                }
                null -> UiText.Dynamic("Invalid data")
            }

            updateState { it.copy(snackbarMessage = msg) }
            return
        }


        updateState {
            it.copy(
                snackbarMessage = UiText.Resource(R.string.success)
            )
        }
    }

    private fun clearSnackbar() {
        updateState { it.copy(snackbarMessage = null) }
    }
}
