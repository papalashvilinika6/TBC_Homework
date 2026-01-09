package com.example.myapplication.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.local.GetTokenUseCase
import com.example.myapplication.domain.usecase.local.IsRememberedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isRememberedUseCase: IsRememberedUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _navigation = Channel<SplashSideEffect>(Channel.BUFFERED)
    val navigation = _navigation.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(3000)

            val remembered = withTimeoutOrNull(1000) { isRememberedUseCase().first() } ?: false
            val token = withTimeoutOrNull(1000) { getTokenUseCase().first() } ?: ""
            val event = if (remembered && token.isNotBlank()) {
                SplashSideEffect.NavigateToHome
            } else {
                SplashSideEffect.NavigateToLogin
            }

            _navigation.send(event)
        }
    }
}


