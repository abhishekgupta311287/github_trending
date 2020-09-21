package com.abhishekgupta.trending.repo.network

import com.abhishekgupta.trending.model.RepositoryDto
import io.reactivex.Single
import retrofit2.http.GET

interface ITrendingApi {

    @GET("/repositories")
    fun getTrendingRepositories(): Single<List<RepositoryDto>>
}