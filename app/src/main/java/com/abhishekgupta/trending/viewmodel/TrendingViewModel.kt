package com.abhishekgupta.trending.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.model.Resource
import com.abhishekgupta.trending.repo.network.ITrendingRepository
import kotlinx.coroutines.launch
import java.util.*

class TrendingViewModel(
    private val repository: ITrendingRepository
) : ViewModel() {

    val trendingLiveData: MutableLiveData<Resource<List<RepositoryDto>>> = MutableLiveData()

    fun requestTrendingRepositories(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            trendingLiveData.value = Resource.Loading()
            val trendingRepositories = repository.getTrendingRepositories(forceRefresh)
            if (trendingRepositories.isNullOrEmpty()) {
                trendingLiveData.value = Resource.Error("List is null or empty")
            } else {
                trendingLiveData.value = Resource.Success(trendingRepositories)
            }

        }
    }

    fun sortByStars() {
        trendingLiveData.value?.data?.let { repos ->
            trendingLiveData.value =
                Resource.Success(repos.sortedByDescending { it.stars }.toList())
        }
    }

    fun sortByName() {
        trendingLiveData.value?.data?.let { repos ->
            trendingLiveData.value =
                Resource.Success(repos.sortedBy { it.name.toLowerCase(Locale.getDefault()) }
                    .toList())
        }
    }

}