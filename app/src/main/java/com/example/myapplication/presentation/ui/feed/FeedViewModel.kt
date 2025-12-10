package com.example.myapplication.presentation.ui.feed

import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.model.Story
import com.example.myapplication.presentation.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.myapplication.domain.usecase.GetPostsUseCase
import com.example.myapplication.domain.usecase.GetStoriesUseCase


@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getPostsUseCase: GetPostsUseCase,
) : BaseViewModel<FeedState, FeedEvent, FeedSideEffect>(FeedState()) {

    override fun onEvent(event: FeedEvent) {
        when (event) {
            FeedEvent.LoadFeed -> loadFeed()
        }
    }

    private fun loadFeed() {
        viewModelScope.launch {

            updateState { it.copy(isLoading = true) }

            var stories = emptyList<Story>()
            var posts = emptyList<Post>()

            getStoriesUseCase().collect { res ->
                when (res) {
                    is Resource.Loader ->
                        updateState { it.copy(isLoading = res.isLoading) }

                    is Resource.Success ->
                        stories = res.data

                    is Resource.Error ->
                        emitSideEffect(FeedSideEffect.ShowError(res.message))
                }
            }

            getPostsUseCase().collect { res ->
                when (res) {
                    is Resource.Loader ->
                        updateState { it.copy(isLoading = res.isLoading) }

                    is Resource.Success ->
                        posts = res.data

                    is Resource.Error ->
                        emitSideEffect(FeedSideEffect.ShowError(res.message))
                }
            }

            val feedItems = buildList {
                if (stories.isNotEmpty()) add(FeedItem.StoriesRow(stories))
                posts.forEach { add(FeedItem.PostRow(it)) }
            }

            updateState {
                it.copy(
                    isLoading = false,
                    items = feedItems
                )
            }
        }
    }
}
