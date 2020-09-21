package com.abhishekgupta.trending.repo.network

import com.abhishekgupta.trending.model.RepositoryDto
import io.reactivex.Single

class TrendingRepositoryImpl(private val api: ITrendingApi) : ITrendingRepository {

    override fun getTrendingRepositories(): Single<List<RepositoryDto>> {
        return api.getTrendingRepositories()
    }

}