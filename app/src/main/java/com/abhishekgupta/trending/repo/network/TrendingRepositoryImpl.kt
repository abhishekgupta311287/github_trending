package com.abhishekgupta.trending.repo.network

import android.content.Context
import com.abhishekgupta.trending.model.RepositoryData
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.repo.db.ITrendingDao
import com.abhishekgupta.trending.scheduler.IScheduler
import com.abhishekgupta.trending.util.getCurrentTimeMillis
import com.abhishekgupta.trending.util.isCacheExpired
import com.abhishekgupta.trending.util.isNetworkAvailable
import io.reactivex.Single

class TrendingRepositoryImpl(
    private val api: ITrendingApi,
    private val dao: ITrendingDao,
    private val scheduler: IScheduler,
    private val context: Context
) : ITrendingRepository {

    override fun getTrendingRepositories(forceRefresh: Boolean): Single<List<RepositoryDto>> {
        return dao.getAllTrendingRepos()
            .subscribeOn(scheduler.io())
            .flatMap { data ->
                if (shouldFetchFromRemote(forceRefresh, data)) {
                    fetchRepoFromApi(data.repos)
                } else {
                    Single.just(data.repos)
                }
            }.onErrorResumeNext {
                fetchRepoFromApi(emptyList())
            }

    }

    private fun shouldFetchFromRemote(forceRefresh: Boolean, data: RepositoryData) =
        isCacheExpired(data.lastRefresh)
                || data.repos.isNullOrEmpty()
                || forceRefresh

    private fun fetchRepoFromApi(repos: List<RepositoryDto>): Single<List<RepositoryDto>> {
        return if (context.isNetworkAvailable() == true) {
            api.getTrendingRepositories()
                .subscribeOn(scheduler.newThread())
                .flatMap {
                    if (it.isNullOrEmpty()) {
                        Single.just(repos)
                    } else {
                        dao.deleteAll()
                        dao.insertTrendingRepos(
                            RepositoryData(getCurrentTimeMillis(), it)
                        )
                        Single.just(it)
                    }
                }
                .onErrorReturn {
                    repos
                }
        } else {
            Single.just(repos)
        }

    }

}