package com.abhishekgupta.trending.repo.db

import com.abhishekgupta.trending.model.RepositoryData
import io.reactivex.Single

class TrendingDaoImpl(private val dao: ITrendingDBDao) : ITrendingDao {
    override fun getAllTrendingRepos(): Single<RepositoryData> = dao.getAllTrendingRepos()

    override fun insertTrendingRepos(repos: RepositoryData) = dao.insert(repos)

    override fun deleteAll() = dao.deleteAll()
}