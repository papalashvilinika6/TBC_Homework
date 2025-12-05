package com.example.myapplication.presentation.ui.cards

import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.repository.CardRepository
import com.example.myapplication.presentation.ui.CardEvent
import com.example.myapplication.presentation.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: CardRepository
) : BaseViewModel<CardEvent, CardState, CardSideEffect>(CardState()) {

     fun createInitialState() = CardState()

     fun handleEvent(event: CardEvent) {
        when (event) {
            CardEvent.Load -> loadCards()
        }
    }

    private fun loadCards() {
        // set loading = true
        _state.value = _state.value.copy(
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {
            val response = repository.getCards()

            if (response.isSuccessful) {

                val domainList = response.body() ?: emptyList()
                val uiList = domainList.map { it.toUi() }

                _state.value = _state.value.copy(
                    uiList,
                    isLoading = false
                )

            } else {

                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Error loading cards"
                )
            }
        }
    }
}
