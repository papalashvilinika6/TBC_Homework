package com.example.myapp.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.usecase.GetPostsUseCase
import com.example.myapp.domain.usecase.GetStoriesUseCase
import com.example.myapp.presentation.common.BaseViewModel
import com.example.myapp.presentation.screen.home.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getPostsUseCase: GetPostsUseCase
) : BaseViewModel<HomeState, HomeEvent>(initialState = HomeState()) {

    private val _sideEffect = Channel<HomeSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        observeHome()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnRefresh -> observeHome(forceRestart = true)
            is HomeEvent.OnStoryClick -> emitSideEffect(HomeSideEffect.NavigateToStory(event.id.toLong()))
            is HomeEvent.OnPostClick -> emitSideEffect(HomeSideEffect.NavigateToPost(event.id.toLong()))
            is HomeEvent.OnLikeClick -> Unit // add later if you have like usecase
        }
    }

    private var observingJobStarted = false

    private fun observeHome(forceRestart: Boolean = false) {
        if (observingJobStarted && !forceRestart) return
        observingJobStarted = true

        viewModelScope.launch {
            combine(
                getStoriesUseCase(),
                getPostsUseCase()
            ) { storiesRes, postsRes ->
                storiesRes to postsRes
            }.collect { (storiesRes, postsRes) ->
                val isLoading = storiesRes is Resource.Loader || postsRes is Resource.Loader

                val stories = (storiesRes as? Resource.Success)?.data?.map { it.toPresentation() }.orEmpty()
                val posts = (postsRes as? Resource.Success)?.data?.map { it.toPresentation() }.orEmpty()

                val errorMessage =
                    (storiesRes as? Resource.Error)?.message
                        ?: (postsRes as? Resource.Error)?.message

                updateState {
                    it.copy(
                        isLoading = isLoading,
                        stories = stories,
                        posts = posts,
                        errorMessage = errorMessage
                    )
                }

                if (errorMessage != null) {
                    emitSideEffect(HomeSideEffect.ShowToast(errorMessage))
                }
            }
        }
    }

    private fun emitSideEffect(effect: HomeSideEffect) = viewModelScope.launch {
        _sideEffect.send(effect)
    }
}
