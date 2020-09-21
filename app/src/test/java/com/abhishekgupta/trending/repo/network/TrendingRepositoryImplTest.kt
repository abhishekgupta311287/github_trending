package com.abhishekgupta.trending.repo.network

import com.abhishekgupta.trending.model.RepositoryDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test

class TrendingRepositoryImplTest {

    @Test
    fun `test atleast one trending repo`() {
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

        val api = mockk<ITrendingApi>()

        val repository = TrendingRepositoryImpl(api)

        every { api.getTrendingRepositories() }.returns(Single.just(listOf(repositoryDto)))

        val trendingRepositories = repository.getTrendingRepositories()

        verify { api.getTrendingRepositories() }

        assert(trendingRepositories.blockingGet().isNotEmpty())
    }

    @Test
    fun `test zero trending repo`() {

        val api = mockk<ITrendingApi>()

        val repository = TrendingRepositoryImpl(api)

        every { api.getTrendingRepositories() }.returns(Single.just(emptyList()))

        val trendingRepositories = repository.getTrendingRepositories()

        verify { api.getTrendingRepositories() }

        assert(trendingRepositories.blockingGet().isEmpty())
    }
}