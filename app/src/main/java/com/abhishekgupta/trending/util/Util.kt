package com.abhishekgupta.trending.util

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.annotation.ColorInt
import com.abhishekgupta.trending.repo.network.TrendingRepositoryImpl
import java.util.*

fun isCacheExpired(lastRefreshTime: Long): Boolean {
    val cal: Calendar = Calendar.getInstance()
    cal.time = Date()
    cal.add(Calendar.HOUR, -TrendingRepositoryImpl.CACHE_EXPIRY_HOURS)

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

@ColorInt
fun parseColor(hexColor: String?, @ColorInt defaultColor: Int): Int {
    hexColor ?: return defaultColor
    return try {
        Color.parseColor(hexColor)
    } catch (e: Exception) {
        // NullPointer, IndexOutOfBounds, IllegalArgument
        defaultColor
    }
}