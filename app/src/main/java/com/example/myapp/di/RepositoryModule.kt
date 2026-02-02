package com.example.myapp.di

import com.example.myapp.data.repository.RegisterRepositoryImpl
import com.example.myapp.domain.repository.RegisterRepository
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
        impl: RegisterRepositoryImpl
    ): RegisterRepository

}