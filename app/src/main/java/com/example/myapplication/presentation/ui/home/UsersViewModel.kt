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

@HiltViewModel
class UsersViewModel @Inject constructor(
    repository: UsersRepository,
) : ViewModel() {

    val usersPaging: Flow<PagingData<User>> =
        repository.getUsersPaging()
            .cachedIn(viewModelScope)
}