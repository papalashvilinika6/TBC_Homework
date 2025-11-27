package com.example.myapplication.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()
    private var splashJob: Job? = null

    fun onEvent(event: SplashEvent) {
        when(event) {
            SplashEvent.OnStopSplash -> onStopSplash()
            SplashEvent.OnStartSplash -> onStartSplash()
        }
    }


    private fun onStartSplash() {
        splashJob = viewModelScope.launch {
            delay(DELAY)
            _sideEffect.emit(SplashSideEffect.NavigateToUser)
        }
    }

    private fun onStopSplash() {
        splashJob?.cancel()
    }

    companion object {
        private const val DELAY = 3000L
    }
}