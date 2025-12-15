package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.location.DefaultLocationClient
import com.example.myapplication.domain.location.LocationClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides @Singleton
    fun provideLocationClient(@ApplicationContext context: Context): LocationClient =
        DefaultLocationClient(context)
}