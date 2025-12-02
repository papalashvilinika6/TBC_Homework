package com.example.myapplication.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

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
            _navigation.emit(SplashSideEffect.NavigateToSecurity)
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
