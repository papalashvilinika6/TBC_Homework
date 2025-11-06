package com.example.myapplication.card

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardViewModel(application: Application) : AndroidViewModel(application) {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards

    init {
        loadInitialCards()
    }

    private fun loadInitialCards() {
        viewModelScope.launch {
            try {
                val context = getApplication<Application>().applicationContext
                val json = context.assets.open("cards.json")
                    .bufferedReader()
                    .use { it.readText() }

                val data = Gson().fromJson(json, CardsWrapper::class.java)
                _cards.value = data.cards
            } catch (e: Exception) {
                e.printStackTrace()
                _cards.value = emptyList()
            }
        }
    }

    fun addCard(card: Card) {
        _cards.value = _cards.value.toMutableList().apply { add(card) }
    }

    fun deleteCard(id: Int) = viewModelScope.launch {
        _cards.emit(_cards.value.filterNot { it.id == id })
    }
}

data class CardsWrapper(
    val cards: List<Card>
)

