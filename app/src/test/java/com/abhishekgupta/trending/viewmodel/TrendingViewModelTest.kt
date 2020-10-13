package com.abhishekgupta.trending.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhishekgupta.trending.di.appModule
import com.abhishekgupta.trending.di.dbModule
import com.abhishekgupta.trending.di.networkModule
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.model.Resource
import com.abhishekgupta.trending.repo.network.ITrendingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class TrendingViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repositoryImpl = mockk<ITrendingRepository>(relaxed = true)

    private val repositoryDto = RepositoryDto(
        "author",
        "Ricky",
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
        stopKoin()
        startKoin {
            listOf(dbModule, networkModule, appModule)
        }

    }

    @After
    fun close() {
        stopKoin()
    }

    @Test
    fun `test success with valid response and force refresh false`() = runBlocking {

        val viewModel = TrendingViewModel(repositoryImpl)

        coEvery { repositoryImpl.getTrendingRepositories(false) }.returns(
            listOf(
                repositoryDto
            )
        )

        viewModel.requestTrendingRepositories(false)

        assert(viewModel.trendingLiveData.value is Resource.Success)

        assert(viewModel.trendingLiveData.value?.data!!.isNotEmpty())

    }

    @Test
    fun `test success with valid response and force refresh true`() {

        val viewModel = TrendingViewModel(repositoryImpl)

        coEvery { repositoryImpl.getTrendingRepositories(true) }.returns(
            listOf(
                repositoryDto
            )
        )

        viewModel.requestTrendingRepositories(true)

        assert(viewModel.trendingLiveData.value is Resource.Success)

        assert(viewModel.trendingLiveData.value?.data!!.isNotEmpty())

    }

    @Test
    fun `test invalid response`() {

        val viewModel = TrendingViewModel(repositoryImpl)

        coEvery { repositoryImpl.getTrendingRepositories() }.returns(emptyList())
        viewModel.requestTrendingRepositories()

        assert(viewModel.trendingLiveData.value is Resource.Error)

    }

    @Test
    fun `test sort by name`() {
        val viewModel = TrendingViewModel(repositoryImpl)
        val list = arrayListOf(repositoryDto)

        list.add(repositoryDto.copy(name = "Abhishek"))

        coEvery { repositoryImpl.getTrendingRepositories() }.returns(list)

        viewModel.requestTrendingRepositories()

        Assert.assertEquals("Ricky", list[0].name)

        viewModel.sortByName()
        Assert.assertEquals("Abhishek", viewModel.trendingLiveData.value?.data?.get(0)?.name)

    }

    @Test
    fun `test sort by stars`() {
        val viewModel = TrendingViewModel(repositoryImpl)
        val list = arrayListOf(repositoryDto)

        list.add(repositoryDto.copy(stars = 100))

        coEvery { repositoryImpl.getTrendingRepositories() }.returns(list)

        viewModel.requestTrendingRepositories()

        Assert.assertEquals(10, list[0].stars)

        viewModel.sortByStars()
        Assert.assertEquals(100, viewModel.trendingLiveData.value?.data?.get(0)?.stars)
    }
}