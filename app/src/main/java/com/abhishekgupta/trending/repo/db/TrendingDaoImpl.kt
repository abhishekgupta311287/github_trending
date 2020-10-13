package com.abhishekgupta.trending.repo.db

import com.abhishekgupta.trending.model.RepositoryData

class TrendingDaoImpl(private val dao: ITrendingDBDao) : ITrendingDao {
    override suspend fun getAllTrendingRepos(): RepositoryData = dao.getAllTrendingRepos()

    override suspend fun insertTrendingRepos(repos: RepositoryData) = dao.insert(repos)

    override suspend fun deleteAll() = dao.deleteAll()
}