package com.example.myapplication.presentation.ui.feed

import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.model.Story
import com.example.myapplication.domain.usecase.GetDriversUseCase
import com.example.myapplication.domain.usecase.GetFavoriteDriverUseCase
import com.example.myapplication.presentation.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.myapplication.domain.usecase.GetPostsUseCase
import com.example.myapplication.domain.usecase.GetStoriesUseCase
import kotlinx.coroutines.flow.first


@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val getFavoriteDriverUseCase: GetFavoriteDriverUseCase,
    private val getDriversUseCase: GetDriversUseCase
) : BaseViewModel<FeedState, FeedEvent, FeedSideEffect>(FeedState()) {

    override fun onEvent(event: FeedEvent) {
        when (event) {
            FeedEvent.LoadFeed -> loadFeed()
        }
    }

    private fun loadFeed() {
        viewModelScope.launch {

            updateState { it.copy(isLoading = true) }

            val stories = when (
                val res = getStoriesUseCase()
                    .first { it !is Resource.Loader }
            ) {
                is Resource.Success -> res.data
                is Resource.Error -> {
                    emitSideEffect(FeedSideEffect.ShowError(res.message))
                    emptyList()
                }
                else -> emptyList()
            }

            val posts = when (
                val res = getPostsUseCase()
                    .first { it !is Resource.Loader }
            ) {
                is Resource.Success -> res.data
                is Resource.Error -> {
                    emitSideEffect(FeedSideEffect.ShowError(res.message))
                    emptyList()
                }
                else -> emptyList()
            }

            // 🔥 TEMP DEBUG: force third item
            val drivers = getDriversUseCase()   // you already have this
            val fakeDriver = drivers.first()

            // 🔽 THIS IS THE PART YOU REPLACE
            val feedItems = buildList {
                if (stories.isNotEmpty()) {
                    add(FeedItem.StoriesRow(stories))
                }

                // 🔥 FORCE THIRD ITEM
                add(FeedItem.MyDriverRow(fakeDriver))

                posts.forEach {
                    add(FeedItem.PostRow(it))
                }
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