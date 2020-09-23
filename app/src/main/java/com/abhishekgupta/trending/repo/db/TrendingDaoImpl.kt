package com.abhishekgupta.trending.repo.db

import com.abhishekgupta.trending.model.RepositoryDto
import io.reactivex.Single

class TrendingDaoImpl(private val dao: ITrendingDBDao) : ITrendingDao {
    override fun getAllTrendingRepos(): Single<List<RepositoryDto>> = dao.getAllTrendingRepos()

    override fun insertTrendingRepos(repos: List<RepositoryDto>) = dao.insert(repos)

    override fun deleteAll() = dao.deleteAll()
}