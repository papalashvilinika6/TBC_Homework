package com.example.myapp.di

import com.example.myapp.data.repository.PostRepositoryImpl
import com.example.myapp.data.repository.StoryRepositoryImpl
import com.example.myapp.domain.repository.PostRepository
import com.example.myapp.domain.repository.StoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStoryRepository(
        impl: StoryRepositoryImpl
    ): StoryRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(
        impl: PostRepositoryImpl
    ): PostRepository
}