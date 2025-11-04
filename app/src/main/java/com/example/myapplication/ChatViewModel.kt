package com.example.myapplication

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date
import java.util.Locale

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val currentList = _messages.value
        val id = currentList.size + 1
        val isRight = id % 2 != 0

        val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            .format(Date())

        val newMessage = ChatMessage(
            id = id,
            text = text,
            date = formattedDate,
            isRightAligned = isRight
        )

        _messages.value = listOf(newMessage) + currentList
    }
}
