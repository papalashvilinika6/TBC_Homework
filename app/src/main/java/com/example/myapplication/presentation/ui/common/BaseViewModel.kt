package com.example.myapplication.presentation.ui.common


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.presentation.ui.cards.CardState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event, State, SideEffect>(
    initialState: CardState
) : ViewModel() {

    val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    open fun onEvent(event: Event){}

    protected fun updateState(update: (State) -> State) {
        _state.value = update(_state.value)
    }

    protected fun emitSideEffect(sideEffect: SideEffect) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }

}
