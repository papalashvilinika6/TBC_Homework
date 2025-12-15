package com.example.myapplication.domain.location

import android.location.Location

interface LocationClient {
    suspend fun getCurrentLocation(): Location?
}