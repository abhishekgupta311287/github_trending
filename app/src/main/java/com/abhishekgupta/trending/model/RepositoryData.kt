package com.abhishekgupta.trending.model

import androidx.room.Entity

@Entity(tableName = "trending_repos_", primaryKeys = ["lastRefresh"])
data class RepositoryData(
    val lastRefresh: Long,
    val repos: List<RepositoryDto>
)