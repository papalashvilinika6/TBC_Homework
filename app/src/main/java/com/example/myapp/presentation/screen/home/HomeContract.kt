package com.example.myapp.presentation.screen.home

import com.example.myapp.presentation.screen.home.model.PostUi
import com.example.myapp.presentation.screen.home.model.StoryUi

data class HomeState(
    val isLoading: Boolean = false,
    val stories: List<StoryUi> = emptyList(),
    val posts: List<PostUi> = emptyList(),
    val errorMessage: String? = null
)

sealed interface HomeEvent {
    data object OnRefresh : HomeEvent
    data class OnStoryClick(val id: Int) : HomeEvent
    data class OnPostClick(val id: Int) : HomeEvent
    data class OnLikeClick(val postId: Int) : HomeEvent
}

sealed interface HomeSideEffect {
    data class NavigateToStory(val id: Long) : HomeSideEffect
    data class NavigateToPost(val id: Long) : HomeSideEffect
    data class ShowToast(val message: String) : HomeSideEffect
}
