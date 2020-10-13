package com.abhishekgupta.trending.repo.network

import com.abhishekgupta.trending.model.RepositoryDto

interface ITrendingRepository {
   suspend fun getTrendingRepositories(forceRefresh:Boolean = false): List<RepositoryDto>
}