package com.abhishekgupta.trending.repo.db

import com.abhishekgupta.trending.model.ContributorDto
import com.abhishekgupta.trending.model.RepositoryDto
import org.junit.Assert
import org.junit.Test

class TrendingConverterTest {

    @Test
    fun fromContributorDtoToMedia() {
        val converter = TrendingConverter()

        val list = converter.fromStringToContributorDto(getContributorJson())

        Assert.assertEquals(2, list.size)
    }

    @Test
    fun fromContributorDtoToString() {
        val converter = TrendingConverter()

        val string = converter.fromContributorDtoToString(getContributorList())

        Assert.assertNotNull(string)
    }

    @Test
    fun fromRepositoryDtoToMedia() {
        val converter = TrendingConverter()

        val list = converter.fromStringToRepositoryDto(getRepositroiesJson())

        Assert.assertEquals(1, list.size)
    }

    @Test
    fun fromRepositoryDtoToString() {
        val converter = TrendingConverter()

        val string = converter.fromRepositoryDtoToString(getRepositoryList())

        Assert.assertNotNull(string)
    }

    private fun getContributorJson(): String {
        return "[\n" +
                "      {\n" +
                "        \"username\": \"mislav\",\n" +
                "        \"href\": \"https://github.com/mislav\",\n" +
                "        \"avatar\": \"https://avatars2.githubusercontent.com/u/887\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"username\": \"vilmibm\",\n" +
                "        \"href\": \"https://github.com/vilmibm\",\n" +
                "        \"avatar\": \"https://avatars3.githubusercontent.com/u/98482\"\n" +
                "      }\n" +
                "    ]"
    }

    private fun getContributorList(): List<ContributorDto> {
        return listOf(
            ContributorDto("name", "href", "avatar")
        )
    }

    private fun getRepositroiesJson(): String {
        return "[\n" +
                "  {\n" +
                "    \"author\": \"cli\",\n" +
                "    \"name\": \"cli\",\n" +
                "    \"avatar\": \"https://github.com/cli.png\",\n" +
                "    \"url\": \"https://github.com/cli/cli\",\n" +
                "    \"description\": \"GitHub’s official command line tool\",\n" +
                "    \"language\": \"Go\",\n" +
                "    \"languageColor\": \"#00ADD8\",\n" +
                "    \"stars\": 14930,\n" +
                "    \"forks\": 988,\n" +
                "    \"currentPeriodStars\": 778,\n" +
                "    \"builtBy\": [\n" +
                "      {\n" +
                "        \"username\": \"mislav\",\n" +
                "        \"href\": \"https://github.com/mislav\",\n" +
                "        \"avatar\": \"https://avatars2.githubusercontent.com/u/887\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]"
    }

    private fun getRepositoryList(): List<RepositoryDto> {
        return listOf(
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
                listOf(
                    ContributorDto("name", "href", "avatar")
                )
            )
        )
    }
}