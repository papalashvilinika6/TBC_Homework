package com.example.myapplication.presentation.ui.splash

import androidx.lifecycle.viewModelScope

import com.example.myapplication.ui.common.BaseViewModel
import com.example.myapplication.ui.splash.SplashEvent
import com.example.myapplication.ui.splash.SplashSideEffect
import com.example.myapplication.ui.splash.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel<SplashState, SplashEvent>(
    initialState = SplashState()
) {

    private var splashJob: Job? = null

    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    override fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.OnStartSplash -> onStartSplash()
            SplashEvent.OnStopSplash -> onStopSplash()
        }
    }

    private fun onStartSplash() {
        if (splashJob != null) return

        splashJob = viewModelScope.launch {
            delay(DELAY)
            emitSideEffect(SplashSideEffect.NavigateToMap)
        }
    }

    private fun onStopSplash() {
        splashJob?.cancel()
        splashJob = null
    }

    private fun emitSideEffect(sideEffect: SplashSideEffect) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }

    companion object {
        private const val DELAY = 3000L
    }
}