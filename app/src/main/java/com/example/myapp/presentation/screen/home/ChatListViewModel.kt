package com.example.myapp.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.myapp.domain.usecase.GetChatsUseCase
import com.example.myapp.domain.model.Resource
import com.example.myapp.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase
) : BaseViewModel<ChatListState, ChatListEvent>(ChatListState()) {

    private val _sideEffect = MutableSharedFlow<ChatListSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        loadChats()
    }

    override fun onEvent(event: ChatListEvent) {
        when (event) {
            is ChatListEvent.OnQueryChanged -> {
                updateState { it.copy(queryInput = event.value) }
            }

            ChatListEvent.OnSearchClick -> {
                val applied = state.value.queryInput.trim()
                updateState { it.copy(queryApplied = applied) }
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
                .onStart { updateState { it.copy(loader = true, error = null) } }
                .collectLatest { res ->
                    when (res) {
                        is Resource.Loader -> updateState { it.copy(loader = res.isLoading) }

                        is Resource.Success -> {
                            val list = res.data
                            updateState {
                                it.copy(
                                    loader = false,
                                    chats = list,
                                    error = null
                                )
                            }
                            applyFilter()
                        }

                        is Resource.Error -> {
                            updateState {
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
        val q = state.value.queryApplied
            .trim()
            .lowercase()

        val base = state.value.chats

        val filtered = if (q.isBlank()) {
            base
        } else {
            base.filter { chat ->
                chat.owner.lowercase().contains(q)
            }
        }

        updateState { it.copy(filteredChats = filtered) }
    }
}

