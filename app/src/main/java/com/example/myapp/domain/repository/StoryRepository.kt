package com.example.myapp.domain.repository

import com.example.myapp.domain.model.Story
import com.example.myapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getStories(): Flow<Resource<List<Story>>>
}