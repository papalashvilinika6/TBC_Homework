package com.example.myapplication.presentation.screen.profile

import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.profile.ProfileRepository
import com.example.myapplication.presentation.screen.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : BaseViewModel<ProfileState, ProfileEvent, ProfileSideEffect>(
    initialState = ProfileState()
) {

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LoadProfile -> loadProfile()
            ProfileEvent.Logout -> logout()
            ProfileEvent.DeleteAccount -> deleteAccount()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            repository.loadProfile()
                .onSuccess { profile ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            name = profile.name,
                            email = profile.email
                        )
                    }
                }
                .onFailure {
                    updateState { it.copy(isLoading = false) }
                    emitSideEffect(
                        ProfileSideEffect.ShowError("")
                    )
                }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            repository.logout()
            emitSideEffect(ProfileSideEffect.NavigateLogin)
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            repository.deleteAccount()
                .onSuccess {
                    emitSideEffect(ProfileSideEffect.NavigateLogin)
                }
                .onFailure {
                    emitSideEffect(
                        ProfileSideEffect.ShowError("")
                    )
                }
        }
    }
}
