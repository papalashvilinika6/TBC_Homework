package com.example.myapplication.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.myapplication.domain.location.LocationClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class DefaultLocationClient(private val context: Context) : LocationClient {
    private val fused: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {
        val last = fused.lastLocation.awaitNullable()
        if (last != null) return last
        return fused.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null).awaitNullable()
    }
}

private suspend fun <T> Task<T>.awaitNullable(): T? =
    suspendCancellableCoroutine { cont ->
        addOnSuccessListener { cont.resume(it) }
        addOnFailureListener { cont.resume(null) }
        addOnCanceledListener { cont.resume(null) }
    }

