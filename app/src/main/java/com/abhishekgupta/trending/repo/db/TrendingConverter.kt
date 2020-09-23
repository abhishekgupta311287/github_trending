package com.abhishekgupta.trending.repo.db

import androidx.room.TypeConverter
import com.abhishekgupta.trending.model.ContributorDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class TrendingConverter {

    @TypeConverter
    fun fromStringToContributorDto(value: String?): List<ContributorDto> {
        val listType =
            object : TypeToken<ArrayList<ContributorDto>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromContributorDtoToString(list: List<ContributorDto>): String? {
        return Gson().toJson(list)
    }
}