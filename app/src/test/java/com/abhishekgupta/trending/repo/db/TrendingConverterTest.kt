package com.abhishekgupta.trending.repo.db

import com.abhishekgupta.trending.model.ContributorDto
import org.junit.Assert
import org.junit.Test

class TrendingConverterTest {

    @Test
    fun fromStringToMedia() {
        val converter = TrendingConverter()

        val list = converter.fromStringToContributorDto(getContributorJson())

        Assert.assertEquals(2, list.size)
    }

    @Test
    fun fromMediaToString() {
        val converter = TrendingConverter()

        val string = converter.fromContributorDtoToString(getContributorList())

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
}