package com.example.myapplication.di

import com.example.myapplication.data.repository.PostRepositoryImpl
import com.example.myapplication.data.repository.StoryRepositoryImpl
import com.example.myapplication.domain.repository.PostRepository
import com.example.myapplication.domain.repository.StoryRepository
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
