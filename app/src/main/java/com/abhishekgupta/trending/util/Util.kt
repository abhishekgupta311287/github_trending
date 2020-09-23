package com.abhishekgupta.trending.util

import android.content.Context
import android.net.ConnectivityManager
import java.util.*

fun isCacheExpired(lastRefreshTime: Long): Boolean {
    val cal: Calendar = Calendar.getInstance()
    cal.time = Date()
    cal.add(Calendar.HOUR, -Constants.CACHE_EXPIRY_HOURS)

    return cal.timeInMillis > lastRefreshTime
}

fun getCurrentTimeMillis(): Long {
    val cal: Calendar = Calendar.getInstance()
    cal.time = Date()

    return cal.timeInMillis
}

fun Context.isNetworkAvailable(): Boolean? {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting
}

object Constants {
    const val CACHE_EXPIRY_HOURS = 2
}