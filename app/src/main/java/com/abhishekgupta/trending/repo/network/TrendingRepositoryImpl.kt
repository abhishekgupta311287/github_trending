package com.abhishekgupta.trending.repo.network

import android.content.Context
import com.abhishekgupta.trending.model.RepositoryData
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.repo.db.ITrendingDao
import com.abhishekgupta.trending.util.getCurrentTimeMillis
import com.abhishekgupta.trending.util.isCacheExpired
import com.abhishekgupta.trending.util.isNetworkAvailable

class TrendingRepositoryImpl(
    private val api: ITrendingApi,
    private val dao: ITrendingDao,
    private val context: Context
) : ITrendingRepository {

    override suspend fun getTrendingRepositories(forceRefresh: Boolean): List<RepositoryDto> {
        return try {
            val (lastRefresh, repos) = dao.getAllTrendingRepos()

            if (shouldFetchFromRemote(forceRefresh, lastRefresh, repos)) {
                fetchRepoFromApi(repos)
            } else {
                repos
            }

        } catch (e: Exception) {
            fetchRepoFromApi(emptyList())
        }


//            .subscribeOn(scheduler.io())
//            .flatMap { data ->
//                if (shouldFetchFromRemote(forceRefresh, data)) {
//                    fetchRepoFromApi(data.repos)
//                } else {
//                    Single.just(data.repos)
//                }
//            }.onErrorResumeNext {
//                fetchRepoFromApi(emptyList())
//            }

    }

    private fun shouldFetchFromRemote(
        forceRefresh: Boolean,
        lastRefresh: Long,
        repos: List<RepositoryDto>
    ) =
        isCacheExpired(lastRefresh)
                || repos.isNullOrEmpty()
                || forceRefresh

    private suspend fun fetchRepoFromApi(repos: List<RepositoryDto>): List<RepositoryDto> {

        if (context.isNetworkAvailable() == false) {
            return repos
        }

        return try {
            val trendingRepositories = api.getTrendingRepositories()

            if (trendingRepositories.isNullOrEmpty()) {
                repos
            } else {
                dao.deleteAll()
                dao.insertTrendingRepos(
                    RepositoryData(getCurrentTimeMillis(), trendingRepositories)
                )
                trendingRepositories
            }
        } catch (e: Exception) {
            repos
        }


//        return
//        if (context.isNetworkAvailable() == true) {
//            api.getTrendingRepositories()
//                .subscribeOn(scheduler.newThread())
//                .flatMap {
//                    if (it.isNullOrEmpty()) {
//                        Single.just(repos)
//                    } else {
//                        dao.deleteAll()
//                        dao.insertTrendingRepos(
//                            RepositoryData(getCurrentTimeMillis(), it)
//                        )
//                        Single.just(it)
//                    }
//                }
//                .onErrorReturn {
//                    repos
//                }
//        } else {
//            Single.just(repos)
//        }
//
    }

    companion object {
        const val CACHE_EXPIRY_HOURS = 2
    }
}