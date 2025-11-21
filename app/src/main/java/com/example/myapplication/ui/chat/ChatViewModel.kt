package com.example.myapplication.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.ChatRepository
import com.example.myapplication.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatSideEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sideEffect: SharedFlow<ChatSideEffect> = _sideEffect.asSharedFlow()

    fun onEvent(event: ChatEvent) {
        when (event) {
            ChatEvent.Load -> loadConversations()
            ChatEvent.OnSearchButtonClicked -> toggleSearch()
            is ChatEvent.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
        }
    }

    private fun loadConversations() {
        viewModelScope.launch {
            repository.getConversations().collect { res ->
                when (res) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = res.isLoading)
                    }
                    is Resource.Success -> {
                        val list = res.data
                        _state.value = _state.value.copy(
                            isLoading = false,
                            conversations = list,
                            filteredConversations = list,
                            errorMessage = null
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = res.message
                        )
                        _sideEffect.emit(ChatSideEffect.ShowError(res.message))
                    }
                }
            }
        }
    }

    private fun toggleSearch() {
        val current = _state.value
        val newEnabled = !current.isSearchEnabled
        val newQuery = if (newEnabled) current.searchQuery else ""
        _state.value = current.copy(
            isSearchEnabled = newEnabled,
            searchQuery = newQuery
        )
        applyFilter(newQuery)
    }

    private fun onSearchQueryChanged(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        applyFilter(query)
    }

    private fun applyFilter(query: String) {
        val all = _state.value.conversations
        if (query.isBlank()) {
            _state.value = _state.value.copy(filteredConversations = all)
            return
        }

        val lower = query.lowercase()
        val filtered = all.filter { conv ->
            conv.owner.lowercase().contains(lower)
        }

        _state.value = _state.value.copy(filteredConversations = filtered)
    }
}
