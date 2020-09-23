package com.abhishekgupta.trending.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.model.Resource
import com.abhishekgupta.trending.repo.network.ITrendingRepository
import com.abhishekgupta.trending.scheduler.IScheduler
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class TrendingViewModel(
    private val repository: ITrendingRepository,
    private val scheduler: IScheduler
) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()
   val trendingLiveData: MutableLiveData<Resource<List<RepositoryDto>>> = MutableLiveData()

    fun requestTrendingRepositories(forceRefresh: Boolean = false) {
        disposable.add(
            repository.getTrendingRepositories(forceRefresh)
                .subscribeOn(scheduler.newThread())
                .observeOn(scheduler.mainThread())
                .doOnSubscribe { trendingLiveData.postValue(Resource.Loading()) }
                .subscribe(
                    {
                        if (it.isNullOrEmpty()) {
                            trendingLiveData.postValue(Resource.Error("List is null or empty"))
                        } else {
                            trendingLiveData.postValue(Resource.Success(it))
                        }
                    },
                    {
                        Log.e("TrendingViewModel", it.message, it)
                        trendingLiveData.postValue(Resource.Error(it.message))
                    })
        )
    }

    fun sortByStars() {
        trendingLiveData.value?.data?.let {repos ->
            trendingLiveData.postValue(
                Resource.Success(repos.sortedByDescending { it.stars }.toList())
            )
        }
    }

    fun sortByName() {
        trendingLiveData.value?.data?.let {repos ->
            trendingLiveData.postValue(
                Resource.Success(repos.sortedBy { it.name.toLowerCase(Locale.getDefault()) }.toList())
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}