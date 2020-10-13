package com.abhishekgupta.trending.di

import com.abhishekgupta.trending.repo.network.ITrendingRepository
import com.abhishekgupta.trending.repo.network.TrendingRepositoryImpl
import com.abhishekgupta.trending.viewmodel.TrendingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ITrendingRepository> { TrendingRepositoryImpl(get(), get(), get()) }
    viewModel { TrendingViewModel(get()) }
}