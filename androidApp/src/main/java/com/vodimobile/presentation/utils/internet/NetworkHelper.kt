package com.vodimobile.presentation.utils.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun getCurrentConnectivityStatus(
    context: Context
): ConnectionStatus {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val connected = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    return if (connected) {
        ConnectionStatus.Available
    }else{
        ConnectionStatus.Unavailable
    }
}

