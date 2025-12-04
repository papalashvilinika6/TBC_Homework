package com.example.myapplication.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.utils.NetworkMonitor
import com.example.myapplication.domain.usecase.FetchUsersUseCase
import com.example.myapplication.domain.usecase.GetUsersDbUseCase
import com.example.myapplication.presentation.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersDb: GetUsersDbUseCase,
    private val fetchUsers: FetchUsersUseCase,
    private val networkMonitor: NetworkMonitor
) : BaseViewModel<UsersState, UsersEvent, UsersSideEffect>(UsersState()) {

    init {
        observeDb()
        observeNetwork()
        onEvent(UsersEvent.Load)
    }

    override fun onEvent(event: UsersEvent) {
        when (event) {
            UsersEvent.Load -> load()
            UsersEvent.Refresh -> refresh()
        }
    }

    private fun observeDb() {
        viewModelScope.launch {
            getUsersDb().collect { users ->
                updateState { it.copy(users = users) }
            }
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkMonitor.isOnline.collect { online ->
                updateState { it.copy(isOnline = online) }
            }
        }
    }

    private fun load() {
        refresh()
    }


    private fun refresh() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            val success = fetchUsers()
            updateState { it.copy(isLoading = false) }

            if (!success) {
                emitSideEffect(UsersSideEffect.ShowOffline)
            }
        }
    }
}

