package com.dk.newssync.data.executor

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

interface BaseSchedulerProvider {

    val computation: CoroutineDispatcher

    val io: CoroutineDispatcher

    val ui: CoroutineDispatcher
}