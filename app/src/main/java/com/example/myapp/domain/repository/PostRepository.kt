package com.example.myapp.domain.repository

import com.example.myapp.domain.model.Post
import com.example.myapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Resource<List<Post>>>
}