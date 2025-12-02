package com.example.myapplication.presentation.ui.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.presentation.ui.security.pin.PinEvent
import com.example.myapplication.presentation.ui.security.pin.PinSideEffect
import com.example.myapplication.presentation.ui.security.pin.PinState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurityViewModel @Inject constructor() : ViewModel() {

    private val correctPin = listOf(0, 9, 3, 4)

    private val _state = MutableStateFlow(PinState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PinSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: PinEvent) {
        when (event) {
            is PinEvent.NumberPressed -> addDigit(event.digit)
            PinEvent.DeletePressed -> removeDigit()
            PinEvent.FingerprintPressed -> {}
        }
    }

    private fun addDigit(digit: Int) {
        val current = _state.value.pin

        if (current.size < 4) {
            val newPin = current + digit
            _state.value = _state.value.copy(pin = newPin)

            if (newPin.size == 4) {
                validatePin(newPin)
            }
        }
    }

    private fun removeDigit() {
        val newPin = _state.value.pin.dropLast(1)
        _state.value = _state.value.copy(pin = newPin)
    }

    private fun validatePin(entered: List<Int>) {
        viewModelScope.launch {
            if (entered == correctPin) {
                _sideEffect.emit(PinSideEffect.Success)
            } else {
                _sideEffect.emit(PinSideEffect.Error)
                delay(200)
                _state.value = PinState(pin = emptyList())
            }
        }
    }
}
