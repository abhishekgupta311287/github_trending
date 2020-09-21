package com.abhishekgupta.trending.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppScheduler : IScheduler {
    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()

    override fun newThread(): Scheduler = Schedulers.newThread()
}