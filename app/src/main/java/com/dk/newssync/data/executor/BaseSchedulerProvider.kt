package com.dk.newssync.data.executor

import io.reactivex.Scheduler

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

interface BaseSchedulerProvider {

    fun computation(): Scheduler

    fun multi(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}