package com.example.myapplication.di

import com.example.myapplication.domain.connectivity.ConnectivityObserver
import com.example.myapplication.data.connectivity.ConnectivityObserverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {
    @Binds @Singleton abstract fun bindConnectivityObserver(
        impl: ConnectivityObserverImpl
    ): ConnectivityObserver
}