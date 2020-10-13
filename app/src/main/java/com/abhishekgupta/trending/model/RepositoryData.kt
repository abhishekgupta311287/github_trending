package com.abhishekgupta.trending.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "trending_repos_", primaryKeys = ["lastRefresh"])
data class RepositoryData(
    @ColumnInfo( name = "lastRefresh") val lastRefresh: Long,
    @ColumnInfo( name = "repos") val repos: List<RepositoryDto>
)