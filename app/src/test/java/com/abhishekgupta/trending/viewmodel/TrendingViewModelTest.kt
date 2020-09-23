package com.abhishekgupta.trending.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhishekgupta.trending.RxImmediateSchedulerRule
import com.abhishekgupta.trending.di.appModule
import com.abhishekgupta.trending.di.dbModule
import com.abhishekgupta.trending.di.networkModule
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.model.Resource
import com.abhishekgupta.trending.repo.network.ITrendingRepository
import com.abhishekgupta.trending.scheduler.IScheduler
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class TrendingViewModelTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

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
    fun `test success with valid response and force refresh false`() {

        val scheduler = mockk<IScheduler>()

        val repositoryImpl = mockk<ITrendingRepository>()

        val viewModel = TrendingViewModel(repositoryImpl, scheduler)

        every { scheduler.newThread() }.returns(Schedulers.trampoline())
        every { scheduler.mainThread() }.returns(Schedulers.trampoline())

        every { repositoryImpl.getTrendingRepositories(false) }.returns(Single.just(listOf(repositoryDto)))

        val trendingRepositories = viewModel.requestTrendingRepositories(false)

        assert(trendingRepositories.value is Resource.Success)

        assert(trendingRepositories.value!!.data!!.isNotEmpty())

    }

    @Test
    fun `test success with valid response and force refresh true`() {

        val scheduler = mockk<IScheduler>()

        val repositoryImpl = mockk<ITrendingRepository>()

        val viewModel = TrendingViewModel(repositoryImpl, scheduler)

        every { scheduler.newThread() }.returns(Schedulers.trampoline())
        every { scheduler.mainThread() }.returns(Schedulers.trampoline())

        every { repositoryImpl.getTrendingRepositories(true) }.returns(Single.just(listOf(repositoryDto)))

        val trendingRepositories = viewModel.requestTrendingRepositories(true)

        assert(trendingRepositories.value is Resource.Success)

        assert(trendingRepositories.value!!.data!!.isNotEmpty())

    }

    @Test
    fun `test error`() {

        val scheduler = mockk<IScheduler>()

        val repositoryImpl = mockk<ITrendingRepository>()

        val viewModel = TrendingViewModel(repositoryImpl, scheduler)

        every { scheduler.newThread() }.returns(Schedulers.trampoline())
        every { scheduler.mainThread() }.returns(Schedulers.trampoline())

        every { repositoryImpl.getTrendingRepositories() }.returns(Single.error(Exception()))

        val trendingRepositories = viewModel.requestTrendingRepositories()

        assert(trendingRepositories.value is Resource.Error)
    }

    @Test
    fun `test invalid response`() {

        val scheduler = mockk<IScheduler>()

        val repositoryImpl = mockk<ITrendingRepository>()

        val viewModel = TrendingViewModel(repositoryImpl, scheduler)

        every { scheduler.newThread() }.returns(Schedulers.trampoline())
        every { scheduler.mainThread() }.returns(Schedulers.trampoline())

        every { repositoryImpl.getTrendingRepositories() }.returns(Single.just(emptyList()))

        val trendingRepositories = viewModel.requestTrendingRepositories()

        assert(trendingRepositories.value is Resource.Error)

    }

}