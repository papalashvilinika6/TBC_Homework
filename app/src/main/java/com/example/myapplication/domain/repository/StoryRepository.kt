package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getStories(): Flow<Resource<List<Story>>>
}