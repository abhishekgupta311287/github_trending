package com.abhishekgupta.trending.di

import androidx.room.Room
import com.abhishekgupta.trending.repo.db.ITrendingDao
import com.abhishekgupta.trending.repo.db.TrendingDaoImpl
import com.abhishekgupta.trending.repo.db.TrendingDb
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(get(), TrendingDb::class.java, "trending.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<ITrendingDao> { TrendingDaoImpl((get() as TrendingDb).trendingDao())}
}