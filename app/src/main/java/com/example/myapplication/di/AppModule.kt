package com.example.myapplication.di

import com.example.myapplication.data.network.ApiService
import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHandleResponse(): HandleResponse = HandleResponse()

    @Provides
    @Singleton
    fun provideChatRepository(
        apiService: ApiService,
        handleResponse: HandleResponse
    ): ChatRepository = ChatRepository(apiService, handleResponse)
}