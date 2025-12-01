package com.example.myapplication.data.di

import com.example.myapplication.domain.repository.AuthLocalRepository
import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.usecase.auth.LoginUseCase
import com.example.myapplication.domain.usecase.auth.RegisterUseCase
import com.example.myapplication.domain.usecase.validate.ValidateEmailUseCase
import com.example.myapplication.domain.usecase.validate.ValidatePasswordUseCase
import com.example.myapplication.domain.usecase.validate.ValidateRepeatPasswordUseCase
import com.example.myapplication.domain.usecase.local.SaveRememberMeUseCase
import com.example.myapplication.domain.usecase.local.GetTokenUseCase
import com.example.myapplication.domain.usecase.local.IsRememberedUseCase
import com.example.myapplication.domain.usecase.auth.LogoutUseCase
import com.example.myapplication.domain.usecase.local.SaveTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides @Singleton
    fun provideValidateEmailUseCase() = ValidateEmailUseCase()

    @Provides @Singleton
    fun provideValidatePasswordUseCase() = ValidatePasswordUseCase()

    @Provides @Singleton
    fun provideValidateRepeatPasswordUseCase() = ValidateRepeatPasswordUseCase()

    @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: AuthRepository
    ) = LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        repository: AuthRepository
    ) = RegisterUseCase(repository)

    @Provides @Singleton
    fun provideSaveTokenUseCase(repo: AuthLocalRepository) = SaveTokenUseCase(repo)

    @Provides @Singleton
    fun provideSaveRememberMeUseCase(repo: AuthLocalRepository) = SaveRememberMeUseCase(repo)

    @Provides @Singleton
    fun provideGetTokenUseCase(repo: AuthLocalRepository) = GetTokenUseCase(repo)

    @Provides @Singleton
    fun provideIsRememberedUseCase(repo: AuthLocalRepository) = IsRememberedUseCase(repo)

    @Provides
    fun provideLogoutUseCase(repo: AuthLocalRepository): LogoutUseCase {
        return LogoutUseCase(repo)
    }
}