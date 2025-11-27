package com.example.myapplication.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.myapplication.data.local.UserData

private val Context.userProtoDataStore: DataStore<UserData> by dataStore(
    fileName = "user_data.pb",
    serializer = UserDataSerializer
)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserData> =
        DataStoreFactory.create(
            serializer = UserDataSerializer,
            produceFile = { context.dataStoreFile("user_data.pb") }
        )
}
