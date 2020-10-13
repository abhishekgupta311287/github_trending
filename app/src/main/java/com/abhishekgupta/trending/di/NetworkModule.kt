package com.abhishekgupta.trending.di

import com.abhishekgupta.trending.BuildConfig
import com.abhishekgupta.trending.repo.network.ITrendingApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
        val okHttpClient = builder.build()
        okHttpClient
    }

    single {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://ghapi.huchen.dev")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ITrendingApi::class.java)
        api
    }
}