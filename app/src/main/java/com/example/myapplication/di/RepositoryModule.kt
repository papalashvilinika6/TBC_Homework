package com.example.myapplication.di

import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.repository.LocationRepositoryImpl
import com.example.myapplication.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides @Singleton
    fun provideRepo(api: ApiService, db: AppDatabase, io: CoroutineDispatcher): LocationRepository =
        LocationRepositoryImpl(api, db.placeDao(), io)
}