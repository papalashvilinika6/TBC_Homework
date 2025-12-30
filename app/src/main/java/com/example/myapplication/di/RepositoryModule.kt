package com.example.myapplication.di

import com.example.myapplication.data.remote.service.ApiService
import com.example.myapplication.data.repository.CategoryRepositoryImpl
import com.example.myapplication.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides @Singleton
    fun provideCategoryRepo(api: ApiService): CategoryRepository =
        CategoryRepositoryImpl(api)
}