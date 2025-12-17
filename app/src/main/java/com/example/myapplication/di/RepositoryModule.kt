package com.example.myapplication.di

import com.example.myapplication.data.profile.ProfileRepository
import com.example.myapplication.data.profile.ProfileRepositoryImpl
import com.example.myapplication.data.repository.DriversRepositoryImpl
import com.example.myapplication.data.repository.FavoriteDriverRepositoryImpl
import com.example.myapplication.data.repository.FirebaseAuthRepository
import com.example.myapplication.data.repository.PostRepositoryImpl
import com.example.myapplication.data.repository.RaceRepositoryImpl
import com.example.myapplication.data.repository.StoryRepositoryImpl
import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.repository.DriversRepository
import com.example.myapplication.domain.repository.FavoriteDriverRepository
import com.example.myapplication.domain.repository.PostRepository
import com.example.myapplication.domain.repository.RaceRepository
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
    abstract fun bindAuthRepository(
        impl: FirebaseAuthRepository
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindDriversRepository(
        impl: DriversRepositoryImpl
    ): DriversRepository

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

    @Binds
    @Singleton
    abstract fun bindFavoriteDriverRepository(
        impl: FavoriteDriverRepositoryImpl
    ): FavoriteDriverRepository

    @Binds
    @Singleton
    abstract fun bindRaceRepository(
        impl: RaceRepositoryImpl
    ): RaceRepository

    @Binds
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

}
