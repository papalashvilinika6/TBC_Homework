package com.example.myapplication.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardViewModel : ViewModel() {
    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards

    init {
        _cards.value = listOf(
            Card(1, "4364     1345     8932     8378", "Sunie Pham", "06/27", 627, Type.MASTERCARD),
            Card(2, "1234     5678     9876     5432", "Nika Papalashvili", "08/28", 273, Type.VISA)
        )
    }

    fun addCard(card: Card) = viewModelScope.launch {
        _cards.emit(_cards.value + card)
    }

    fun deleteCard(id: Int) = viewModelScope.launch {
        _cards.emit(_cards.value.filterNot { it.id == id })
    }
}
