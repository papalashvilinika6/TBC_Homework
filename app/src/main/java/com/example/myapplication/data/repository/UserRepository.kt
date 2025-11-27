package com.example.myapplication.data.repository

import androidx.datastore.core.DataStore
import com.example.myapplication.data.local.UserData
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataStore: DataStore<UserData>
) {

    suspend fun updateUser(update: UserData.Builder.() -> Unit) {
        dataStore.updateData { currentData ->
            currentData.toBuilder().apply(update).build()
        }
    }

    suspend fun saveUser(firstName: String? = null, lastName: String? = null, email: String? = null) {
        updateUser {
            firstName?.let { setFirstName(it) }
            lastName?.let { setLastName(it) }
            email?.let { setEmail(it) }
        }
    }

    suspend fun readUser(): UserData = dataStore.data.first()
}
