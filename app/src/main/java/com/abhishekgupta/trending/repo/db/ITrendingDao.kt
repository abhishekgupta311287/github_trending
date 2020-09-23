package com.abhishekgupta.trending.repo.db

import com.abhishekgupta.trending.model.RepositoryData
import io.reactivex.Single

interface ITrendingDao {

    fun getAllTrendingRepos(): Single<RepositoryData>

    fun insertTrendingRepos(repos: RepositoryData)

    fun deleteAll()
}