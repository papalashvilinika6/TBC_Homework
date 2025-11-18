package com.example.myapplication.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.datastore.DataStoreManager
import com.example.myapplication.data.network.RetrofitClient
import com.example.myapplication.data.repository.AuthRepository

class LoginViewModelFactory(private val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val repository = AuthRepository(RetrofitClient.loginApiService, dataStoreManager,
                RetrofitClient.registerApiService)
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}