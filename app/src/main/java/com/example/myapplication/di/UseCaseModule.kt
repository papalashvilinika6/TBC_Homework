package com.example.myapplication.di

import com.example.myapplication.domain.repository.LocationRepository
import com.example.myapplication.domain.usecase.GetPlacesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides @Singleton
    fun provideGetPlaces(repo: LocationRepository) = GetPlacesUseCase(repo)
}