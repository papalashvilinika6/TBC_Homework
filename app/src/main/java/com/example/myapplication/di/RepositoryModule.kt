package com.example.myapplication.di

import com.example.myapplication.data.repository.DriversRepositoryImpl
import com.example.myapplication.data.repository.FirebaseAuthRepository
import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.repository.DriversRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        impl: FirebaseAuthRepository
    ): AuthRepository

    @Binds
    abstract fun bindDriversRepository(
        impl: DriversRepositoryImpl
    ): DriversRepository
}
