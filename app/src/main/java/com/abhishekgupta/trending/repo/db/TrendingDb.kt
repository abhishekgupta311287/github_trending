package com.abhishekgupta.trending.repo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abhishekgupta.trending.model.RepositoryDto

@Database(entities = [RepositoryDto::class], version = 1, exportSchema = false)
@TypeConverters(TrendingConverter::class)
abstract class TrendingDb : RoomDatabase() {

    abstract fun trendingDao(): ITrendingDBDao
}