package com.abhishekgupta.trending.repo.db

import com.abhishekgupta.trending.model.RepositoryData

interface ITrendingDao {

    suspend fun getAllTrendingRepos(): RepositoryData

    suspend fun insertTrendingRepos(repos: RepositoryData)

    suspend fun deleteAll()
}