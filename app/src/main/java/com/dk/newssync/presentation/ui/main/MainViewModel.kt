package com.dk.newssync.presentation.ui.main

import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.usecase.EntriesUseCase
import com.dk.newssync.presentation.ui.base.BaseViewModel
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.common.SingleLiveEvent
import com.dk.newssync.presentation.common.defaultErrorHandler
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 03/01/2019.
 **/

class MainViewModel @Inject constructor(private val entriesUseCase: EntriesUseCase): BaseViewModel() {

    val selectedEntry: SingleLiveEvent<State<Entry>> =
        SingleLiveEvent()

    fun findSelected() {
        entriesUseCase.getSelected()
            .doOnSubscribe { selectedEntry.postValue(State.loading()) }
            .doOnNext { entry -> selectedEntry.postValue(State.success(entry)) }
            .doOnError { e -> selectedEntry.postValue(State.error(e.message ?: "Unknown Error", e)) }
            .subscribeBy(onError = defaultErrorHandler())
            .addTo(compositeDisposable)
    }

}