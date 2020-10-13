package com.abhishekgupta.trending.repo.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStreamReader

class TrendingApiTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var api: ITrendingApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ITrendingApi::class.java)

    }

    @Test
    fun `trending repos success`() = runBlocking {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(readStringFromFile("success.json"))
            }
        }
        val repos = api.getTrendingRepositories()
        val request = mockWebServer.takeRequest()

        Assert.assertThat(request.path, CoreMatchers.`is`("/repositories"))

        Assert.assertThat(repos.size, CoreMatchers.`is`(1))

        val repo = repos[0]
        Assert.assertThat(repo.author, CoreMatchers.`is`("cli"))
        Assert.assertThat(repo.name, CoreMatchers.`is`("cli"))
        Assert.assertThat(repo.avatar, CoreMatchers.`is`("https://github.com/cli.png"))
        Assert.assertThat(repo.url, CoreMatchers.`is`("https://github.com/cli/cli"))
        Assert.assertThat(
            repo.description,
            CoreMatchers.`is`("GitHub’s official command line tool")
        )
        Assert.assertThat(repo.language, CoreMatchers.`is`("Go"))
        Assert.assertThat(repo.languageColor, CoreMatchers.`is`("#00ADD8"))
        Assert.assertThat(repo.stars, CoreMatchers.`is`(14930))
        Assert.assertThat(repo.forks, CoreMatchers.`is`(988))
        Assert.assertThat(repo.currentPeriodStars, CoreMatchers.`is`(778))

        val builtBy = repo.builtBy
        Assert.assertThat(builtBy.size, CoreMatchers.`is`(5))
        Assert.assertThat(builtBy[0].username, CoreMatchers.`is`("mislav"))
        Assert.assertThat(
            builtBy[0].avatar,
            CoreMatchers.`is`("https://avatars2.githubusercontent.com/u/887")
        )
        Assert.assertThat(builtBy[0].href, CoreMatchers.`is`("https://github.com/mislav"))

    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    private fun readStringFromFile(fileName: String): String {
        try {
            val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }

}