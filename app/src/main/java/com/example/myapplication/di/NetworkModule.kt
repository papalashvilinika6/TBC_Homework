package com.example.myapplication.di

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.remote.DriversApi
import com.example.myapplication.data.remote.PostApi
import com.example.myapplication.data.remote.RaceApi
import com.example.myapplication.data.remote.StoryApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = BuildConfig.BASE_URL + BuildConfig.API_VERSION

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }


    @Provides
    @Singleton
    fun provideRetrofit(json: Json, client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideDriversApi(
        retrofit: Retrofit
    ): DriversApi {
        return retrofit.create(DriversApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostApi(retrofit: Retrofit): PostApi =
        retrofit.create(PostApi::class.java)

    @Provides
    @Singleton
    fun provideStoryApi(retrofit: Retrofit): StoryApi =
        retrofit.create(StoryApi::class.java)

    @Provides
    @Singleton
    fun provideRaceApi(
        retrofit: Retrofit
    ): RaceApi =
        retrofit.create(RaceApi::class.java)


}
