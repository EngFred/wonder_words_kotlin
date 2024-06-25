package com.kotlin.wonderwords.features.quotes.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo

//fun isInternetAvailable(context: Context): Boolean {
//    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    val network = connectivityManager.activeNetwork ?: return false
//    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
//    return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//}