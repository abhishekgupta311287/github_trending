package com.abhishekgupta.trending.repo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhishekgupta.trending.model.RepositoryData

@Dao
interface ITrendingDBDao {

    @Query("SELECT * FROM trending_repos_")
    suspend fun getAllTrendingRepos(): RepositoryData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: RepositoryData)

    @Query("DELETE FROM trending_repos_")
    suspend fun deleteAll()

}