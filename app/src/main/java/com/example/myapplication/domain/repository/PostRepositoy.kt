package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Resource
import com.example.myapplication.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Resource<List<Post>>>
}