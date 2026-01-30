package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.Post
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {

    operator fun invoke(): Flow<Resource<List<Post>>> {
        return repository.getPosts()
    }
}
