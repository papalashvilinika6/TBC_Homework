package com.example.myapplication.data.connectivity

import android.content.Context
import android.net.*
import androidx.core.content.getSystemService
import com.example.myapplication.domain.connectivity.ConnectivityObserver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ConnectivityObserverImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ConnectivityObserver {
    private val cm = context.getSystemService<ConnectivityManager>()!!

    override val isConnected: Flow<Boolean> = callbackFlow {
        val initial = cm.activeNetwork?.let { n ->
            cm.getNetworkCapabilities(n)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } ?: false
        trySend(initial)
        val cb = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(n: Network, c: NetworkCapabilities) {
                trySend(c.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
            }
            override fun onUnavailable() { trySend(false) }
            override fun onLost(n: Network) { trySend(false) }
            override fun onAvailable(n: Network) { trySend(true) }
        }
        cm.registerDefaultNetworkCallback(cb)
        awaitClose { cm.unregisterNetworkCallback(cb) }
    }
}
