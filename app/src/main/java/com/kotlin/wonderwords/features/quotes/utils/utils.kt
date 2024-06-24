package com.kotlin.wonderwords.features.quotes.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnected == true
}