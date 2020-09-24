package com.abhishekgupta.trending.view

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.abhishekgupta.trending.R
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.model.Resource
import com.abhishekgupta.trending.viewmodel.TrendingViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class TrendingFragmentTest {
    @get:Rule
    val activityRule = ActivityTestRule(TrendingActivity::class.java, false, false)

    private val trendingLiveData: MutableLiveData<Resource<List<RepositoryDto>>> = MutableLiveData()
    private val model = mockk<TrendingViewModel>(relaxed = true)
    private val myModule = module {
        viewModel { model }
    }

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            modules(
                listOf(
                    myModule
                )
            )
        }
        every { model.trendingLiveData }.returns(trendingLiveData)
    }

    private val repositoryDto = RepositoryDto(
        "author",
        "Ricky",
        "avatar",
        "url",
        "description",
        "English",
        "langcolor",
        100,
        20,
        50,
        emptyList()
    )

    @Test
    fun testSuccessStateUI() {
        activityRule.launchActivity(Intent())
        trendingLiveData.postValue(Resource.Success(arrayListOf(repositoryDto)))

        Espresso.onView(ViewMatchers.withId(R.id.swipeRefresh))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Ricky"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("author"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.shimmerLayout))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        Espresso.onView(ViewMatchers.withId(R.id.errorLayout))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    }

    @Test
    fun testPullToRefreshAction() {
        activityRule.launchActivity(Intent())
        trendingLiveData.postValue(Resource.Success(arrayListOf(repositoryDto)))

        Espresso.onView(ViewMatchers.withId(R.id.swipeRefresh))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Ricky"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.swipeRefresh))
            .perform(ViewActions.swipeDown())

        Espresso.onView(ViewMatchers.withId(R.id.shimmerLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        trendingLiveData.postValue(Resource.Loading())
        Espresso.onView(ViewMatchers.withId(R.id.swipeRefresh))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        trendingLiveData.postValue(Resource.Success(arrayListOf(repositoryDto)))

        Espresso.onView(ViewMatchers.withId(R.id.swipeRefresh))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        Espresso.onView(ViewMatchers.withId(R.id.shimmerLayout))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        Espresso.onView(ViewMatchers.withId(R.id.errorLayout))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        Espresso.onView(ViewMatchers.withText("Ricky"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun performClickOnItem() {
        activityRule.launchActivity(Intent())
        trendingLiveData.postValue(Resource.Success(arrayListOf(repositoryDto.copy(language = "Spanish", stars = 110, forks = 80))))

        Espresso.onView(ViewMatchers.withText("Ricky"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.repoItem))
            .perform(ViewActions.click())

        Thread.sleep(100)

        Espresso.onView(ViewMatchers.withId(R.id.moreDetails))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        Espresso.onView(ViewMatchers.withId(R.id.language))
            .check(ViewAssertions.matches(ViewMatchers.withText("Spanish")))

        Espresso.onView(ViewMatchers.withId(R.id.stars))
            .check(ViewAssertions.matches(ViewMatchers.withText("110")))

        Espresso.onView(ViewMatchers.withId(R.id.forks))
            .check(ViewAssertions.matches(ViewMatchers.withText("80")))

        Espresso.onView(ViewMatchers.withId(R.id.repoItem))
            .perform(ViewActions.click())

        Thread.sleep(100)

        Espresso.onView(ViewMatchers.withId(R.id.moreDetails))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

    }

    @Test
    fun testLoadingStateUI() {
        activityRule.launchActivity(Intent())

        Espresso.onView(ViewMatchers.withId(R.id.shimmerLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testErrorStateUI() {
        activityRule.launchActivity(Intent())
        trendingLiveData.postValue(Resource.Error("Test Error"))

        Espresso.onView(ViewMatchers.withId(R.id.errorLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.errorTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.errorSubTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.errorImage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.retry))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun performRetryButtonClick() {
        activityRule.launchActivity(Intent())
        trendingLiveData.postValue(Resource.Error("Test Error"))

        Espresso.onView(ViewMatchers.withId(R.id.retry))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.shimmerLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        trendingLiveData.postValue(Resource.Success(arrayListOf(repositoryDto)))

        Espresso.onView(ViewMatchers.withId(R.id.swipeRefresh))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Ricky"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @After
    fun teardown() {
        stopKoin()
    }
}