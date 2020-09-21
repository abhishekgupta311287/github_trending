package com.abhishekgupta.trending.repo.network

import com.abhishekgupta.trending.model.RepositoryDto
import io.reactivex.Single

interface ITrendingRepository {
    fun getTrendingRepositories(): Single<List<RepositoryDto>>
}