package com.example.myapplication.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.data.dto.User
import com.example.myapplication.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UsersRepository,
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.FetchUsers -> fetchUsers(event.page)
        }
    }

    private fun fetchUsers(page: Int = 1) {
        viewModelScope.launch {
            try {
                val list = repository.getUsers(page)
                _users.value = list
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    val usersPaging: Flow<PagingData<User>> =
        repository.getUsersPaging()
            .cachedIn(viewModelScope)
}