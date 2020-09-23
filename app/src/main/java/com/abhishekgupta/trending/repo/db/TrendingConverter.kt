package com.abhishekgupta.trending.repo.db

import androidx.room.TypeConverter
import com.abhishekgupta.trending.model.ContributorDto
import com.abhishekgupta.trending.model.RepositoryDto
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

    @TypeConverter
    fun fromStringToRepositoryDto(value: String?): List<RepositoryDto> {
        val listType =
            object : TypeToken<ArrayList<RepositoryDto>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromRepositoryDtoToString(list: List<RepositoryDto>): String? {
        return Gson().toJson(list)
    }
}