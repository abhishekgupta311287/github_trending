package com.abhishekgupta.trending

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.abhishekgupta.trending.di.appModule
import com.abhishekgupta.trending.di.dbModule
import com.abhishekgupta.trending.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TrendingApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TrendingApplication)
            modules(
                listOf(
                    dbModule,
                    networkModule,
                    appModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)

    }
}