package com.example.myapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.domain.usecase.GetChatsUseCase
import com.example.myapp.domain.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatListState())
    val state: StateFlow<ChatListState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatListSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        loadChats()
    }

    fun onEvent(event: ChatListEvent) {
        when (event) {
            is ChatListEvent.OnQueryChanged -> {
                _state.update { it.copy(queryInput = event.value) }
            }

            ChatListEvent.OnSearchClick -> {
                val applied = _state.value.queryInput.trim()
                _state.update { it.copy(queryApplied = applied) }
                applyFilter()
            }

            is ChatListEvent.OnChatClick -> {
                viewModelScope.launch {
                    _sideEffect.emit(ChatListSideEffect.NavigateToChat(event.chatId))
                }
            }
        }
    }

    private fun loadChats() {
        viewModelScope.launch {
            getChatsUseCase()
                .onStart { _state.update { it.copy(loader = true, error = null) } }
                .collectLatest { res ->
                    when (res) {
                        is Resource.Loader -> _state.update { it.copy(loader = res.isLoading) }

                        is Resource.Success -> {
                            val list = res.data
                            _state.update {
                                it.copy(
                                    loader = false,
                                    chats = list,
                                    error = null
                                )
                            }
                            applyFilter()
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    loader = false,
                                    error = res.message
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun applyFilter() {
        val q = _state.value.queryApplied
            .trim()
            .lowercase()

        val base = _state.value.chats

        val filtered = if (q.isBlank()) {
            base
        } else {
            base.filter { chat ->
                chat.owner.lowercase().contains(q)
            }
        }

        _state.update { it.copy(filteredChats = filtered) }
    }
}
