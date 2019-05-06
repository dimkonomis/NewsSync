package com.dk.newssync.presentation.ui

import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 2019-05-06.
 **/

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class DispatcherRule : TestWatcher() {
    val mainThreadSurrogate = newSingleThreadContext("UI thread")

    override fun starting(description: Description) {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}