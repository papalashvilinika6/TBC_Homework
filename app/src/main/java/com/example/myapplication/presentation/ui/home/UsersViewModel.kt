package com.example.myapplication.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.data.repository.UsersRepositoryImpl
import com.example.myapplication.domain.model.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    repository: UsersRepositoryImpl,
) : ViewModel() {

    val usersPaging: Flow<PagingData<GetUsers>> =
        repository.getUsersPaging()
            .cachedIn(viewModelScope)
}