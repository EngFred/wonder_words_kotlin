package com.kotlin.wonderwords.features.quotes.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class ConnectivityReceiver(private val onNetworkChange: (Boolean) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val isConnected = isNetworkAvailable(context)
        onNetworkChange(isConnected)
    }

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
    }

}