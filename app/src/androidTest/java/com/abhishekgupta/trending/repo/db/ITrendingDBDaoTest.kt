package com.abhishekgupta.trending.repo.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.abhishekgupta.trending.model.ContributorDto
import com.abhishekgupta.trending.model.RepositoryDto
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ITrendingDBDaoTest {

    private val contributorList = listOf(
        ContributorDto("name", "href", "avatar")
    )

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
        contributorList
    )
    private val repositoryList = arrayListOf(
        repositoryDto
    )

    private lateinit var db: TrendingDb

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, TrendingDb::class.java)
            .fallbackToDestructiveMigration()
            .build()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        db.trendingDao().deleteAll()
        db.close()
    }

    @Test
    fun insert() {
        db.trendingDao().insert(repositoryList)

        val repos = db.trendingDao().getAllTrendingRepos()

        Assert.assertEquals(1, repos.blockingGet().size)
    }

    @Test
    fun deleteAll() {
        repositoryList.add(repositoryDto.copy(author = "author1"))
        db.trendingDao().insert(repositoryList)

        var repos = db.trendingDao().getAllTrendingRepos()

        Assert.assertEquals(2, repos.blockingGet().size)

        db.trendingDao().deleteAll()

        repos = db.trendingDao().getAllTrendingRepos()

        assert(repos.blockingGet().isEmpty())
    }

    @Test
    fun getAllTrendingRepos() {
        repositoryList.add(repositoryDto.copy(author = "author1"))
        repositoryList.add(repositoryDto.copy(author = "author2"))
        db.trendingDao().insert(repositoryList)

        val repos = db.trendingDao().getAllTrendingRepos()

        Assert.assertEquals(3, repos.blockingGet().size)

    }
}