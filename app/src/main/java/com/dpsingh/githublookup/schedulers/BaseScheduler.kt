package com.dpsingh.githublookup.schedulers

import android.support.annotation.NonNull
import io.reactivex.Scheduler


interface BaseScheduler {

    @NonNull
    fun io(): Scheduler

    @NonNull
    fun ui(): Scheduler

}