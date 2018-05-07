package com.dpsingh.githublookup.schedulers

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val testScheduler: Scheduler) : BaseScheduler {

    override fun io(): Scheduler {
        return testScheduler
    }

    override fun ui(): Scheduler {
        return testScheduler
    }

}