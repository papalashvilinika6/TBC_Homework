package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke() = repository.getPosts()
}