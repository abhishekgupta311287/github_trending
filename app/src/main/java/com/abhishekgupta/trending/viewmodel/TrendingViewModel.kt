package com.abhishekgupta.trending.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhishekgupta.trending.model.RepositoryDto
import com.abhishekgupta.trending.model.Resource
import com.abhishekgupta.trending.repo.network.ITrendingRepository
import com.abhishekgupta.trending.scheduler.IScheduler
import io.reactivex.disposables.CompositeDisposable

class TrendingViewModel(
    private val repository: ITrendingRepository,
    private val scheduler: IScheduler
) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val trendingLiveData: MutableLiveData<Resource<List<RepositoryDto>>> = MutableLiveData()

    fun requestTrendingRepositories(): LiveData<Resource<List<RepositoryDto>>> {

        fetchTrendingRepositories()

        return trendingLiveData
    }

    private fun fetchTrendingRepositories() {
        disposable.add(
            repository.getTrendingRepositories()
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

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}