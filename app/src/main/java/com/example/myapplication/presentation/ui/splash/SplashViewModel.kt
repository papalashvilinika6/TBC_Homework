package com.example.myapplication.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.local.GetTokenUseCase
import com.example.myapplication.domain.usecase.local.IsRememberedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isRememberedUseCase: IsRememberedUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _navigation = MutableSharedFlow<SplashSideEffect>()
    val navigation = _navigation.asSharedFlow()

    private var splashJob: Job? = null

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.OnStartSplash -> onStartSplash()
            SplashEvent.OnStopSplash -> onStopSplash()
        }
    }

    private fun onStartSplash() {
        if (splashJob != null) return


        splashJob = viewModelScope.launch {
            delay(DELAY)

            val remembered = isRememberedUseCase().first()
            val token = getTokenUseCase().first()

            if (remembered && token.isNotBlank()) {
                _navigation.emit(SplashSideEffect.NavigateToHome)
            } else {
                _navigation.emit(SplashSideEffect.NavigateToLogin)
            }
        }
    }

    private fun onStopSplash() {
        splashJob?.cancel()
        splashJob = null
    }

    companion object {
        private const val DELAY = 3000L
    }
}
