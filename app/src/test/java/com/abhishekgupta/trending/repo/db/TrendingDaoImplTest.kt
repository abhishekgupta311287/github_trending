package com.abhishekgupta.trending.repo.db

import com.abhishekgupta.trending.model.ContributorDto
import com.abhishekgupta.trending.model.RepositoryData
import com.abhishekgupta.trending.model.RepositoryDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
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
    fun getAllTrendingRepos() {
        val trendingDao = TrendingDaoImpl(dao)

        every { dao.getAllTrendingRepos() }.returns(Single.just(data))

        val repos = trendingDao.getAllTrendingRepos()

        assertEquals(1, repos.blockingGet().repos.size)
        assertEquals(1, repos.blockingGet().repos[0].builtBy.size)
        assertEquals(10, repos.blockingGet().lastRefresh)

    }

    @Test
    fun insertTrendingRepos() {

        val trendingDao = TrendingDaoImpl(dao)

        trendingDao.insertTrendingRepos(data)

        verify { dao.insert(data) }
    }

    @Test
    fun deleteAll() {
        val trendingDao = TrendingDaoImpl(dao)

        trendingDao.deleteAll()

        verify { dao.deleteAll() }
    }
}