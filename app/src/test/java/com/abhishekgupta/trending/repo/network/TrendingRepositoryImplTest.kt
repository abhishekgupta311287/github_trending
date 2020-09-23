package com.abhishekgupta.trending.repo.network

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhishekgupta.trending.RxImmediateSchedulerRule
import com.abhishekgupta.trending.model.RepositoryData
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.repo.db.ITrendingDao
import com.abhishekgupta.trending.scheduler.IScheduler
import com.abhishekgupta.trending.util.getCurrentTimeMillis
import com.abhishekgupta.trending.util.isNetworkAvailable
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TrendingRepositoryImplTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val api = mockk<ITrendingApi>(relaxed = true)
    private val dao = mockk<ITrendingDao>(relaxed = true)
    private val context = mockk<Context>(relaxed = true)
    private val scheduler = mockk<IScheduler>(relaxed = true)

    val repositoryDto = RepositoryDto(
        "author",
        "name",
        "avatar",
        "url",
        "desc",
        "lang",
        "langcolor",
        10,
        10,
        10,
        emptyList()
    )

    @Before
    fun setup() {
        every { scheduler.newThread() }.returns(Schedulers.trampoline())
        every { scheduler.mainThread() }.returns(Schedulers.trampoline())
        every { scheduler.io() }.returns(Schedulers.trampoline())

    }

    @Test
    fun `test repo availability from db and no api call`() {

        val repository = TrendingRepositoryImpl(api, dao, scheduler, context)

        every { dao.getAllTrendingRepos() }.returns(
            Single.just(
                RepositoryData(
                    getCurrentTimeMillis(),
                    listOf(repositoryDto)
                )
            )
        )
        every { context.isNetworkAvailable() }.returns(true)

        every { api.getTrendingRepositories() }.returns(Single.just(listOf(repositoryDto)))

        val trendingRepositories = repository.getTrendingRepositories()

        verify(exactly = 0) { api.getTrendingRepositories() }

        assert(trendingRepositories.blockingGet().isNotEmpty())
    }

    @Test
    fun `test no db data and no api call`() {

        val repository = TrendingRepositoryImpl(api, dao, scheduler, context)

        every { dao.getAllTrendingRepos() }.returns(
            Single.just(
                RepositoryData(
                    0,
                    emptyList()
                )
            )
        )
        every { context.isNetworkAvailable() }.returns(true)

        every { api.getTrendingRepositories() }.returns(Single.just(listOf(repositoryDto)))

        val trendingRepositories = repository.getTrendingRepositories()

        assert(trendingRepositories.blockingGet().isNotEmpty())
    }

    @Test
    fun `test db error`() {

        val repository = TrendingRepositoryImpl(api, dao, scheduler, context)

        every { dao.getAllTrendingRepos() }.returns(
            Single.error(Exception("db error"))
        )
        every { context.isNetworkAvailable() }.returns(true)

        every { api.getTrendingRepositories() }.returns(Single.just(listOf(repositoryDto)))

        val trendingRepositories = repository.getTrendingRepositories()

        assert(trendingRepositories.blockingGet().isNotEmpty())
    }

    @Test
    fun `test no db data and no api call error`() {

        val repository = TrendingRepositoryImpl(api, dao, scheduler, context)

        every { dao.getAllTrendingRepos() }.returns(
            Single.just(
                RepositoryData(
                    0,
                    emptyList()
                )
            )
        )
        every { context.isNetworkAvailable() }.returns(true)

        every { api.getTrendingRepositories() }.returns(Single.error(Exception("Network failure")))

        val trendingRepositories = repository.getTrendingRepositories()

        assert(trendingRepositories.blockingGet().isEmpty())
    }

    @Test
    fun `test offline data availability and force refresh`() {

        val repository = TrendingRepositoryImpl(api, dao, scheduler, context)

        every { dao.getAllTrendingRepos() }.returns(
            Single.just(
                RepositoryData(
                    getCurrentTimeMillis(),
                    listOf(repositoryDto)
                )
            )
        )
        every { context.isNetworkAvailable() }.returns(false)

        every { api.getTrendingRepositories() }.returns(Single.just(listOf(repositoryDto)))

        val trendingRepositories = repository.getTrendingRepositories(true)

        verify(exactly = 0) { api.getTrendingRepositories() }

        assert(trendingRepositories.blockingGet().isNotEmpty())
    }

    @Test
    fun `test data availability when api retrun empty list`() {

        val repository = TrendingRepositoryImpl(api, dao, scheduler, context)

        every { dao.getAllTrendingRepos() }.returns(
            Single.just(
                RepositoryData(
                    getCurrentTimeMillis(),
                    listOf(repositoryDto)
                )
            )
        )
        every { context.isNetworkAvailable() }.returns(true)

        every { api.getTrendingRepositories() }.returns(Single.just(emptyList()))

        val trendingRepositories = repository.getTrendingRepositories(true)

        verify(exactly = 0) { api.getTrendingRepositories() }

        assert(trendingRepositories.blockingGet().isNotEmpty())
    }

    @Test
    fun `test no data availability when api return empty list and no data in db`() {

        val repository = TrendingRepositoryImpl(api, dao, scheduler, context)

        every { dao.getAllTrendingRepos() }.returns(
            Single.just(
                RepositoryData(
                    0,
                    emptyList()
                )
            )
        )
        every { context.isNetworkAvailable() }.returns(true)

        every { api.getTrendingRepositories() }.returns(Single.just(emptyList()))

        val trendingRepositories = repository.getTrendingRepositories(true)

        verify(exactly = 0) { api.getTrendingRepositories() }

        assert(trendingRepositories.blockingGet().isEmpty())
    }

}