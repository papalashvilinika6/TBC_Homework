package com.example.myapplication.presentation.screen.splash

import androidx.lifecycle.viewModelScope

import com.example.myapplication.presentation.screen.common.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : BaseViewModel<SplashState, SplashEvent, SplashSideEffect>(
    initialState = SplashState()
) {

    private var splashJob: Job? = null

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

            if (auth.currentUser != null) {
                emitSideEffect(SplashSideEffect.NavigateToHome)
            } else {
                emitSideEffect(SplashSideEffect.NavigateToLogin)
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

