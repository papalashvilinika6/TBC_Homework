package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.model.Story
import com.example.myapp.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepository
) {

    operator fun invoke(): Flow<Resource<List<Story>>> {
        return repository.getStories()
    }
}