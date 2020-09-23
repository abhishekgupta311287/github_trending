package com.abhishekgupta.trending.repo.db

import com.abhishekgupta.trending.model.RepositoryDto
import io.reactivex.Single

interface ITrendingDao {

    fun getAllTrendingRepos(): Single<List<RepositoryDto>>

    fun insertTrendingRepos(repos: List<RepositoryDto>)

    fun deleteAll()
}