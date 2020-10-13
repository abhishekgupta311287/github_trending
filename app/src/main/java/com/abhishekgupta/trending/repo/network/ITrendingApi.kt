package com.abhishekgupta.trending.repo.network

import com.abhishekgupta.trending.model.RepositoryDto
import retrofit2.http.GET

interface ITrendingApi {

    @GET("/repositories")
    suspend fun getTrendingRepositories(): List<RepositoryDto>
}