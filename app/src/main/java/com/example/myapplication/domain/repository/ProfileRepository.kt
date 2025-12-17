package com.example.myapplication.data.profile

import com.example.myapplication.data.model.ProfileData

interface ProfileRepository {

    suspend fun loadProfile(): Result<ProfileData>

    suspend fun logout()

    suspend fun deleteAccount(): Result<Unit>
}