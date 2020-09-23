package com.abhishekgupta.trending.repo.network

import com.abhishekgupta.trending.model.RepositoryDto
import io.reactivex.Single

interface ITrendingRepository {
    fun getTrendingRepositories(forceRefresh:Boolean = false): Single<List<RepositoryDto>>
}