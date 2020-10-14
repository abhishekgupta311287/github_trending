package com.abhishekgupta.trending.repo.db

import com.abhishekgupta.trending.model.ContributorDto
import com.abhishekgupta.trending.model.RepositoryData
import com.abhishekgupta.trending.model.RepositoryDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class TrendingDaoImplTest {

    private val dao = mockk<ITrendingDBDao>(relaxed = true)

    private val contributorList = listOf(
        ContributorDto("name", "href", "avatar")
    )

    private val repositoryList = listOf(
        RepositoryDto(
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
            contributorList
        )
    )

    private val data = RepositoryData(
        10,
        repositoryList
    )

    @Test
    fun getAllTrendingRepos() = runBlocking {
        val trendingDao = TrendingDaoImpl(dao)

        coEvery { dao.getAllTrendingRepos() }.returns(data)

        val repos = trendingDao.getAllTrendingRepos()

        assertEquals(1, repos.repos.size)
        assertEquals(1, repos.repos[0].builtBy.size)
        assertEquals(10, repos.lastRefresh)

    }

    @Test
    fun insertTrendingRepos() = runBlocking {

        val trendingDao = TrendingDaoImpl(dao)

        trendingDao.insertTrendingRepos(data)

        coVerify { dao.insert(data) }
    }

    @Test
    fun deleteAll() = runBlocking {
        val trendingDao = TrendingDaoImpl(dao)

        trendingDao.deleteAll()

        coVerify { dao.deleteAll() }
    }
}