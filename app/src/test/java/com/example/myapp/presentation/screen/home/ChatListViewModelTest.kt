package com.example.myapp.presentation.screen.home

import app.cash.turbine.test
import com.example.myapp.domain.model.Chat
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.ChatRepository
import com.example.myapp.domain.usecase.GetChatsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatListViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private class FakeChatRepository(
        private val flow: Flow<Resource<List<Chat>>>
    ) : ChatRepository {
        override fun getChats(): Flow<Resource<List<Chat>>> = flow
    }

    @Test
    fun `init loads chats and fills filteredChats`() = runTest {
        val chats = listOf(
            Chat(1, "", "Alice", "Hi", "1:00", 0, false, "text"),
            Chat(2, "", "Bob", "Yo", "2:00", 0, false, "text"),
        )
        val repo = FakeChatRepository(flowOf(Resource.Success(chats)))
        val useCase = GetChatsUseCase(repo)

        val vm = ChatListViewModel(useCase)
        advanceUntilIdle()

        val state = vm.state.value
        Assert.assertFalse(state.loader)
        Assert.assertNull(state.error)
        Assert.assertEquals(2, state.chats.size)
        Assert.assertEquals(2, state.filteredChats.size)
    }

    @Test
    fun `OnQueryChanged updates queryInput`() = runTest {
        val repo = FakeChatRepository(flowOf(Resource.Success(emptyList())))
        val useCase = GetChatsUseCase(repo)
        val vm = ChatListViewModel(useCase)
        advanceUntilIdle()

        vm.onEvent(ChatListEvent.OnQueryChanged("ali"))

        Assert.assertEquals("ali", vm.state.value.queryInput)
    }

    @Test
    fun `OnSearchClick filters by owner`() = runTest {
        val chats = listOf(
            Chat(1, "", "Alice Smith", "", "", 0, false, "text"),
            Chat(2, "", "Bob", "", "", 0, false, "text"),
        )
        val repo = FakeChatRepository(flowOf(Resource.Success(chats)))
        val useCase = GetChatsUseCase(repo)
        val vm = ChatListViewModel(useCase)
        advanceUntilIdle()

        vm.onEvent(ChatListEvent.OnQueryChanged("alice"))
        vm.onEvent(ChatListEvent.OnSearchClick)

        val state = vm.state.value
        Assert.assertEquals("alice", state.queryApplied)
        Assert.assertEquals(1, state.filteredChats.size)
        Assert.assertEquals("Alice Smith", state.filteredChats.first().owner)
    }

    @Test
    fun `OnChatClick emits NavigateToChat side effect`() = runTest {
        val repo = FakeChatRepository(flowOf(Resource.Success(emptyList())))
        val useCase = GetChatsUseCase(repo)
        val vm = ChatListViewModel(useCase)
        advanceUntilIdle()

        vm.sideEffect.test {
            vm.onEvent(ChatListEvent.OnChatClick(7))
            Assert.assertEquals(ChatListSideEffect.NavigateToChat(7), awaitItem())
        }
    }

    @Test
    fun `init sets error when repository returns error`() = runTest {
        val repo = FakeChatRepository(flowOf(Resource.Error("Network error")))
        val useCase = GetChatsUseCase(repo)
        val vm = ChatListViewModel(useCase)
        advanceUntilIdle()

        val state = vm.state.value
        Assert.assertEquals("Network error", state.error)
        Assert.assertFalse(state.loader)
    }
}