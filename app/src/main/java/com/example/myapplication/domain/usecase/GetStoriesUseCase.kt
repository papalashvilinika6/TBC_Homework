package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepository
) {
    operator fun invoke() = repository.getStories()
}
