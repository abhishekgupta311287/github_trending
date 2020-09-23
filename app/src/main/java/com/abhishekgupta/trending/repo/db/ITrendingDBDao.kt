package com.abhishekgupta.trending.repo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhishekgupta.trending.model.RepositoryDto
import io.reactivex.Single

@Dao
interface ITrendingDBDao {

    @Query("SELECT * FROM trending_repos_")
    fun getAllTrendingRepos(): Single<List<RepositoryDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<RepositoryDto>)

    @Query("DELETE FROM trending_repos_")
    fun deleteAll()

}