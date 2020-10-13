package com.abhishekgupta.trending.repo.network

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhishekgupta.trending.model.RepositoryData
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.repo.db.ITrendingDao
import com.abhishekgupta.trending.util.getCurrentTimeMillis
import com.abhishekgupta.trending.util.isNetworkAvailable
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class TrendingRepositoryImplTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val api = mockk<ITrendingApi>(relaxed = true)
    private val dao = mockk<ITrendingDao>(relaxed = true)
    private val context = mockk<Context>(relaxed = true)

    private val repositoryDto = RepositoryDto(
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

    @Test
    fun `test repo availability from db and no api call`() = runBlocking {

        val repository = TrendingRepositoryImpl(api, dao, context)

        coEvery { dao.getAllTrendingRepos() }.returns(
            RepositoryData(
                getCurrentTimeMillis(),
                listOf(repositoryDto)
            )
        )
        coEvery { context.isNetworkAvailable() }.returns(true)

        coEvery { api.getTrendingRepositories() }.returns(listOf(repositoryDto))

        val trendingRepositories = repository.getTrendingRepositories()

        coVerify(exactly = 0) { api.getTrendingRepositories() }

        assert(trendingRepositories.isNotEmpty())
    }

    @Test
    fun `test no db data and no api call`() = runBlocking {

        val repository = TrendingRepositoryImpl(api, dao, context)

        coEvery { dao.getAllTrendingRepos() }.returns(
            RepositoryData(
                0,
                emptyList()
            )
        )
        coEvery { context.isNetworkAvailable() }.returns(true)

        coEvery { api.getTrendingRepositories() }.returns(listOf(repositoryDto))

        val trendingRepositories = repository.getTrendingRepositories()

        assert(trendingRepositories.isNotEmpty())
    }

    @Test
    fun `test db error`() = runBlocking {

        val repository = TrendingRepositoryImpl(api, dao, context)

        coEvery { dao.getAllTrendingRepos() }.throws(Exception())
        coEvery { context.isNetworkAvailable() }.returns(true)

        coEvery { api.getTrendingRepositories() }.returns(listOf(repositoryDto))

        val trendingRepositories = repository.getTrendingRepositories()

        assert(trendingRepositories.isNotEmpty())
    }

    @Test
    fun `test no db data and no api call error`() = runBlocking {

        val repository = TrendingRepositoryImpl(api, dao, context)

        coEvery { dao.getAllTrendingRepos() }.returns(
            RepositoryData(
                0,
                emptyList()
            )
        )
        coEvery { context.isNetworkAvailable() }.returns(true)

        coEvery { api.getTrendingRepositories() }.throws(Exception())

        val trendingRepositories = repository.getTrendingRepositories()

        assert(trendingRepositories.isEmpty())
    }

    @Test
    fun `test offline data availability and force refresh`() = runBlocking {

        val repository = TrendingRepositoryImpl(api, dao, context)

        coEvery { dao.getAllTrendingRepos() }.returns(
            RepositoryData(
                getCurrentTimeMillis(),
                listOf(repositoryDto)
            )
        )
        coEvery { context.isNetworkAvailable() }.returns(false)

        coEvery { api.getTrendingRepositories() }.returns(listOf(repositoryDto))

        val trendingRepositories = repository.getTrendingRepositories(true)

        coVerify(exactly = 0) { api.getTrendingRepositories() }

        assert(trendingRepositories.isNotEmpty())
    }

    @Test
    fun `test data availability when api return empty list`() = runBlocking {

        val repository = TrendingRepositoryImpl(api, dao, context)

        coEvery { dao.getAllTrendingRepos() }.returns(
            RepositoryData(
                getCurrentTimeMillis(),
                listOf(repositoryDto)
            )
        )
        coEvery { context.isNetworkAvailable() }.returns(true)

        coEvery { api.getTrendingRepositories() }.returns(emptyList())

        val trendingRepositories = repository.getTrendingRepositories(true)

        assert(trendingRepositories.isNotEmpty())
    }

    @Test
    fun `test no data availability when api return empty list and no data in db`() = runBlocking {

        val repository = TrendingRepositoryImpl(api, dao, context)

        coEvery { dao.getAllTrendingRepos() }.returns(
            RepositoryData(
                0,
                emptyList()
            )
        )
        coEvery { context.isNetworkAvailable() }.returns(true)

        coEvery { api.getTrendingRepositories() }.returns(emptyList())

        val trendingRepositories = repository.getTrendingRepositories(true)

        assert(trendingRepositories.isEmpty())
    }

}