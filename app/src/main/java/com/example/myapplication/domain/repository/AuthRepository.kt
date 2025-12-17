package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun signUpWithEmail(
        email: String,
        password: String,
        name: String
    ): User
    suspend fun signInWithEmail(email: String, password: String): User
    suspend fun signInWithGoogle(idToken: String): User
    suspend fun signOut()
}