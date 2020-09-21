package com.abhishekgupta.trending.scheduler

import io.reactivex.Scheduler

interface IScheduler {
    fun mainThread(): Scheduler

    fun io(): Scheduler

    fun newThread(): Scheduler
}