package com.dk.newssync.data.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Singleton
class SchedulerProvider : BaseSchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun multi(): Scheduler {
        return Schedulers.newThread()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}