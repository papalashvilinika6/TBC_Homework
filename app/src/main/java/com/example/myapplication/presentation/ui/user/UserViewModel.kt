package com.example.myapplication.presentation.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.Save -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                    repository.saveUser(
                        firstName = event.firstName,
                        lastName = event.lastName,
                        email = event.email
                    )
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
            UserEvent.Read -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                    val user = repository.readUser()
                    _state.value = _state.value.copy(
                        loadedUser = user,
                        isLoading = false
                    )
                }
            }
        }
    }
}
