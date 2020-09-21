package com.abhishekgupta.trending.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*

class AppSchedulerTest {

    @Test
    fun mainThread() {
        val scheduler : IScheduler = AppScheduler()
        assertNotNull(scheduler.mainThread())
        assertEquals(AndroidSchedulers.mainThread(), scheduler.mainThread())
    }

    @Test
    fun io() {
        val scheduler : IScheduler = AppScheduler()
        assertNotNull(scheduler.io())
        assertEquals(Schedulers.io(), scheduler.io())
    }

    @Test
    fun newThread() {
        val scheduler : IScheduler = AppScheduler()
        assertNotNull(scheduler.newThread())
        assertEquals(Schedulers.newThread(), scheduler.newThread())
    }
}